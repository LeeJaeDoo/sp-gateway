package com.sp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.*
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.*
import org.springframework.cloud.openfeign.*
import org.springframework.web.reactive.config.*

@SpringBootApplication
@EnableEurekaClient
@ConfigurationPropertiesScan
@EnableFeignClients
@EnableWebFlux
class SpApplication

fun main(args: Array<String>) {
    runApplication<SpApplication>(*args)
}
