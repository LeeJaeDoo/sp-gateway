package com.sp.filter.extensions

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.fasterxml.jackson.module.kotlin.*
import java.time.*
import java.time.format.*

/**
 * @author Jaedoo Lee
 */
val objectMapper: ObjectMapper = ObjectMapper().apply {
    registerModule(KotlinModule(nullToEmptyCollection = true, nullToEmptyMap = true, nullisSameAsDefault = true))
    registerModule(JavaTimeModule().apply {
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        addDeserializer(
            LocalDateTime::class.java,
            LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
    })
    enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}

fun Any.toJson(): String = objectMapper.writeValueAsString(this)
