package com.sp.filter

import com.sp.application.model.*
import com.sp.filter.FrontHeadersConstant.ACCESS_TOKEN_HEADER
import com.sp.filter.FrontHeadersConstant.ATTRIBUTE_NAME
import com.sp.filter.FrontHeadersConstant.TEST_ACCESS_TOKEN
import org.springframework.cloud.gateway.filter.*
import org.springframework.cloud.gateway.filter.factory.*
import org.springframework.core.env.*
import org.springframework.http.server.reactive.*
import org.springframework.stereotype.*

/**
 * @author Jaedoo Lee
 */
@Component
class FrontAccessTokenGatewayFilterFactory(
    environment: Environment,
    private val frontAccessTokenService: FrontAccessTokenService
) : AbstractGatewayFilterFactory<Any>() {

    private val developStage: Boolean by lazy { "local" in environment.activeProfiles || "alpha" in environment.activeProfiles }

    override fun apply(config: Any?) = GatewayFilter { exchange, chain ->
        with (exchange.request) {
            chain.filter(exchange.mutate().request(decorate(this)).build())
        }
    }

    private fun decorate(request: ServerHttpRequest): ServerHttpRequest {
        val token = request.headers.getFirst(ACCESS_TOKEN_HEADER)
        return when {
            token == null || isTestToken(token) -> request.mutate().build()
            else -> request.mutate()
                .headers { it.set(ATTRIBUTE_NAME, frontAccessTokenService.checkMember(token)) }
                .build()
        }
    }

    private fun isTestToken(token: String) : Boolean =
        developStage && token == TEST_ACCESS_TOKEN
}
