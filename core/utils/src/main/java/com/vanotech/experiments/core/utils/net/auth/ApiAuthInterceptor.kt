package com.vanotech.experiments.core.utils.net.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection

// retry with fresh tokens on 403
@Suppress("unused")
open class ApiAuthInterceptor(
    private val authStore: ApiAuthStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = authStore.authoriseRequest(request)
        val response = chain.proceed(newRequest)

        if (canRetry(response)) {
            if (canRetry(response.request)) {
                response.close()

                authStore.getFreshTokens()
                val retryRequest = authStore.authoriseRequest(request) {
                    it.tag(RetryTag::class.java, RetryTag)
                }
                return chain.proceed(retryRequest)
            }
        }
        return response
    }

    private fun canRetry(request: Request): Boolean {
        return request.tag(RetryTag::class.java) == null
    }

    protected open fun canRetry(response: Response): Boolean {
        return response.code == HttpURLConnection.HTTP_FORBIDDEN
    }

    private object RetryTag
}