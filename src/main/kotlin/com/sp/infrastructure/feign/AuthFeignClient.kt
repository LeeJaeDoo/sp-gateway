package com.sp.infrastructure.feign

import com.sp.application.model.*
import org.springframework.cloud.openfeign.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

/**
 * @author Jaedoo Lee
 */
@FeignClient("auth-internal", configuration = [FeignConfig::class])
interface AuthFeignClient {

    @GetMapping(
        value = ["internal/auth/token/check"],
        headers = ["Version=1.0"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun checkToken(@RequestHeader accessToken: String): MemberInfo

}
