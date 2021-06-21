package com.sp.infrastructure.feign.adapter

import com.sp.application.model.*
import com.sp.filter.*
import com.sp.infrastructure.feign.*
import feign.*
import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.stereotype.*

/**
 * @author Jaedoo Lee
 */
@Component
class MemberAdapter: FrontAccessTokenService {

    @Lazy
    @Autowired
    private lateinit var memberFeignClient: MemberFeignClient

    override fun checkMember(accessToken: String): String {
        return try {
            memberFeignClient.checkToken(accessToken)
        } catch (e: FeignException) {
            if (e.status() == HttpStatus.UNAUTHORIZED.value()) {
                throw InvalidTokenException()
            }
            throw e
        }
    }
}
