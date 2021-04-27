package com.sp.infrastructure.configuration

import com.sp.filter.extensions.*
import org.springframework.context.annotation.*
import org.springframework.format.*
import org.springframework.http.codec.*
import org.springframework.http.codec.json.*
import org.springframework.web.reactive.config.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class WebFluxConfiguration : WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().apply {
            jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
            jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
        }
    }

//    override fun addFormatters(registry: FormatterRegistry) {
//        registry.apply {
//            addConverter(HttpLocalDateTimeConverter())
//            addConverter(HttpLocalDateConverter())
//        }
//    }
}
