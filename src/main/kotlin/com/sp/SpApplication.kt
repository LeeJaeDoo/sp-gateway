package com.sp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.*
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.*

@SpringBootApplication
@EnableEurekaClient
@ConfigurationPropertiesScan
class SpApplication

fun main(args: Array<String>) {
    runApplication<SpApplication>(*args)
}
