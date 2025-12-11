package com.vanotech.experiments.core.utils.net.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

open class HeadersInterceptor(
    private val headers: Map<String, String>
) : Interceptor {
    constructor(
        key: String,
        value: String
    ) : this(
        mapOf(
            key to value
        )
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = newRequest(request)
        return chain.proceed(newRequest)
    }

    private fun newRequest(request: Request): Request {
        return request.newBuilder().also {
            onNewRequest(it)
        }.build()
    }

    protected open fun onNewRequest(builder: Request.Builder) {
        headers.forEach { (key, value) ->
            builder.header(key, value)
        }
    }
}