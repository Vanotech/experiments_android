package com.vanotech.experiments.core.utils.net.auth

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// retry with fresh tokens on 401
@Suppress("unused")
open class ApiAuthenticator(
    private val authStore: ApiAuthStore
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        authStore.getFreshTokens()
        val request = response.request
        return authStore.authoriseRequest(request)
    }
}