package com.vanotech.experiments.core.utils.net.interceptors

import com.vanotech.experiments.core.utils.net.Http

@Suppress("unused")
class UserAgentInterceptor(
    userAgent: String
) : HeadersInterceptor(
    key = Http.Header.USER_AGENT,
    value = userAgent
)