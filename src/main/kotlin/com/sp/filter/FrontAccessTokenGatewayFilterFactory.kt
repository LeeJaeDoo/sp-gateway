package com.sp.filter

import com.sp.application.model.FrontAccessTokenService
import com.sp.filter.extensions.*
import org.springframework.cloud.gateway.filter.*
import org.springframework.cloud.gateway.filter.factory.*
import org.springframework.stereotype.*

/**
 * @author Jaedoo Lee
 */
@Component
class FrontAccessTokenGatewayFilterFactory(
    private val frontAccessTokenService: FrontAccessTokenService
) : AbstractGatewayFilterFactory<Any>() {
    override fun apply(config: Any?) = GatewayFilter { exchange, chain ->
        val request = exchange.request

        when (val token = request.headers.getFirst("accessToken")?.takeIf { it.isNotEmpty() }) {
            null -> chain.filter(exchange.mutate().request(request.mutate().build()).build())
            else -> frontAccessTokenService.checkMember(token)
                .let {
                    chain.filter(exchange.mutate().request(request.mutate()
                    .header("Member-Info", it)
                    .build()).build())
                }
        }
    }
}
