@file:Suppress("unused")

package com.vanotech.experiments.core.utils

object Http {
    object Header {
        const val AUTHORIZATION = "authorization"

        object Auth {
            fun basic(token: String?): String = "Basic ${token.orEmpty()}"
            fun bearer(token: String?): String = "Bearer ${token.orEmpty()}"
        }
    }
}