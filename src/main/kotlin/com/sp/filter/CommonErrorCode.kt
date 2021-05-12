package com.sp.filter

/**
 * @author Jaedoo Lee
 */
enum class CommonErrorCode(override val simpleCode: String) : ErrorCode {
    REQUEST_ERROR("E9001"),
    ;

    override val code: String
        get() = "error.common.$simpleCode"
}
