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
class AuthAdapter: FrontAccessTokenService {

    @Lazy
    @Autowired
    private lateinit var authFeignClient: AuthFeignClient

    override fun checkMember(accessToken: String): String {
        return try {
            authFeignClient.checkToken(accessToken)
        } catch (e: FeignException) {
            if (e.status() == HttpStatus.UNAUTHORIZED.value()) {
                throw InvalidTokenException()
            }
            throw e
        }
    }
}
