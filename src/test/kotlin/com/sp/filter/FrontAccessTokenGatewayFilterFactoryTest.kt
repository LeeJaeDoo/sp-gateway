package com.sp.filter

import com.sp.application.model.*
import com.sp.filter.FrontHeadersConstant.ACCESS_TOKEN_HEADER
import com.sp.filter.FrontHeadersConstant.ATTRIBUTE_NAME
import com.sp.filter.FrontHeadersConstant.TEST_ACCESS_TOKEN
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*
import org.springframework.core.env.*
import org.springframework.mock.http.server.reactive.*
import org.springframework.mock.web.server.*
import reactor.core.publisher.*

/**
 * @author Jaedoo Lee
 */
@ExtendWith(MockKExtension::class)
internal class FrontAccessTokenGatewayFilterFactoryTest {

    @MockK
    private lateinit var frontAccessTokenService: FrontAccessTokenService

    @MockK
    private lateinit var environment: Environment

    private lateinit var filterFactory: FrontAccessTokenGatewayFilterFactory

    private val config: Any? = null

    @BeforeEach
    fun setUp() {
        filterFactory = FrontAccessTokenGatewayFilterFactory(environment, frontAccessTokenService)
    }

    @Test
    fun `헤더에 토큰이 없을 경우`() {
        val exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/members/register").build())

        filterFactory.apply(config).filter(exchange) {
            assertAll(
                { Assertions.assertNull(it.request.headers[ATTRIBUTE_NAME]) },
                { Assertions.assertNull(it.request.headers[ACCESS_TOKEN_HEADER])}
            )
            Mono.empty()
        }

        verify(inverse = true) {
            frontAccessTokenService.checkMember(any())
        }
    }

    @Test
    fun `헤더에 토큰이 test-access-token인 경우`() {
        val exchange = MockServerWebExchange.from(
            MockServerHttpRequest.put("/members/profile")
                .header(ACCESS_TOKEN_HEADER, TEST_ACCESS_TOKEN)
                .build()
        )

        every { environment.activeProfiles } returns arrayOf("local")

        filterFactory.apply(config).filter(exchange) {
            assertAll(
                { Assertions.assertNull(it.request.headers[ATTRIBUTE_NAME]) },
                { Assertions.assertEquals(TEST_ACCESS_TOKEN, it.request.headers[ACCESS_TOKEN_HEADER]?.firstOrNull())}
            )
            Mono.empty()
        }

        verify(inverse = true) {
            frontAccessTokenService.checkMember(any())
        }
    }

    @Test
    fun `헤더에 실제 토큰(payload)이 있는 경우`() {
        val testPayload = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJubyI6MTAsIm5pY2tuYW1lIjoi65GQ65GQ65GQ65GQIiwiaXNzIjoiU1AiLCJleHAiOjE2MjA1NzQyNDMsImlhdCI6MTYyMDU3MjQ0MywiZW1haWwiOiJkbHdvZW45QG5hdmVyLmNvbSJ9.acG2pJtK5MoUq2PCi5rRuTtpaxoDoSy_RJeptYkp8mU"
        val exchange = MockServerWebExchange.from(
            MockServerHttpRequest.put("/members/profile")
                .header(ACCESS_TOKEN_HEADER, testPayload)
                .build()
        )

        every { environment.activeProfiles } returns arrayOf("local")
        every { frontAccessTokenService.checkMember(any()) } returns testPayload

        filterFactory.apply(config).filter(exchange) {
            assertAll(
                { Assertions.assertEquals(testPayload, it.request.headers[ATTRIBUTE_NAME]?.firstOrNull()) },
                { Assertions.assertEquals(testPayload, it.request.headers[ACCESS_TOKEN_HEADER]?.firstOrNull())}
            )
            Mono.empty()
        }

        verify {
            frontAccessTokenService.checkMember(any())
        }
    }
}
