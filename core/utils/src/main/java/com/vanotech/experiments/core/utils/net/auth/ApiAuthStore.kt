package com.vanotech.experiments.core.utils.net.auth

import com.vanotech.experiments.core.utils.net.Http
import okhttp3.Request


abstract class ApiAuthStore {

    protected var accessTokenType = ""
    protected var accessToken = ""

    private fun getCredentials() = Http.Auth.token(accessTokenType, accessToken)

    open fun authoriseRequest(
        request: Request,
        block: (Request.Builder) -> Unit = {}
    ): Request {
        val newRequest = request.newBuilder()
            .header(Http.Header.AUTHORIZATION, getCredentials())
            .apply(block)
            .build()
        return newRequest
    }

    abstract fun getFreshTokens()
}