package com.sp.infrastructure.feign.adapter

import com.sp.application.model.*
import com.sp.infrastructure.feign.*
import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.Lazy
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
        return authFeignClient.checkToken(accessToken)
    }
}
