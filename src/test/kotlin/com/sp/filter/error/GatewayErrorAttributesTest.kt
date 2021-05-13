package com.sp.filter.error

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.*

/**
 * @author Jaedoo Lee
 */
internal class GatewayErrorAttributesTest {

    @Test
    fun getErrorAttributes() {
        val attributes = linkedMapOf<String, Any>().also {
            it["timestamp"] = LocalDateTime.MAX
        }

        assertEquals(LocalDateTime.MAX, attributes["timestamp"])
    }

}
