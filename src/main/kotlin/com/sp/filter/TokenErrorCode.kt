package com.sp.filter

/**
 * @author Jaedoo Lee
 */
enum class TokenErrorCode(override val simpleCode: String) : ErrorCode {
    INVALID_TOKEN("T0001"),
    ;

    override val code: String
        get() = "error.token.$simpleCode"
}
