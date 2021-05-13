package com.sp.filter

/**
 * @author Jaedoo Lee
 */
abstract class TokenException(val errorCode: ErrorCode) : RuntimeException() {

    override val message: String = MessageConverter.getMessage(errorCode.code)
}
