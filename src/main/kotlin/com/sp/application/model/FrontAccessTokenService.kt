package com.sp.application.model

/**
 * @author Jaedoo Lee
 */
interface FrontAccessTokenService {
    fun checkMember(accessToken: String): String
}
