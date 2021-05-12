package com.sp.filter.error

import com.sp.filter.*
import org.springframework.beans.*
import org.springframework.boot.web.reactive.error.*
import org.springframework.core.codec.*
import org.springframework.http.*
import org.springframework.stereotype.*
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.*
import java.time.*

/**
 * @author Jaedoo Lee
 */
@Component
class GatewayErrorAttributes : ErrorAttributes {
    override fun getError(request: ServerRequest): Throwable {
        return request.attributeOrNull("GATEWAY") as Throwable?
            ?: IllegalStateException("Missing exception attribute in ServerWebExchange")
    }

    override fun getErrorAttributes(request: ServerRequest, includeStackTrace: Boolean): Map<String, Any> =
        linkedMapOf<String, Any>().also {
            it["timestamp"] = LocalDateTime.now()
            it["path"] = "${request.methodName()} ${request.path()}"
            val error = getError(request)
            val httpStatus = determineHttpStatus(error)
            it["status"] = httpStatus.value()
            it["error"] = httpStatus.reasonPhrase
            it["code"] = determineCode(error)
            it["message"] = determineMessage(error, request.path().startsWith("/internal"))
        }

    override fun storeErrorInformation(error: Throwable?, exchange: ServerWebExchange) {
        exchange.attributes.putIfAbsent("GATEWAY", error)
    }

    private fun determineHttpStatus(error: Throwable): HttpStatus = when (error) {
        is ResponseStatusException -> error.status
        is TypeMismatchException, is DecodingException, is NumberFormatException -> HttpStatus.BAD_REQUEST
        is TokenException -> HttpStatus.UNAUTHORIZED
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    private fun determineCode(error: Throwable): String = when (error) {
        is TypeMismatchException, is DecodingException, is NumberFormatException -> CommonErrorCode.REQUEST_ERROR.simpleCode
        is TokenException -> error.errorCode.simpleCode
        else -> "99999"
    }

    private fun determineMessage(error: Throwable, internalApi: Boolean): String = when (error) {
        is ResponseStatusException -> error.cause?.message ?: error.reason ?: ""
        is TypeMismatchException, is DecodingException, is NumberFormatException ->
            MessageConverter.getMessage(CommonErrorCode.REQUEST_ERROR.code)
        is TokenException -> error.message
        else -> if (internalApi) "${error.message}" else "Server error"
    }
}
