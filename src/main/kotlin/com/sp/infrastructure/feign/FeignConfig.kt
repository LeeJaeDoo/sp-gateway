package com.sp.infrastructure.feign

import com.sp.filter.extensions.*
import org.springframework.beans.factory.*
import org.springframework.boot.autoconfigure.http.*
import org.springframework.context.annotation.*
import org.springframework.http.converter.*
import org.springframework.http.converter.json.*
import kotlin.streams.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class FeignConfig {
    @Bean
    fun messageConverters(converters: ObjectProvider<HttpMessageConverter<*>>): HttpMessageConverters {
        return HttpMessageConverters(false, converters.orderedStream().toList())
    }
}
