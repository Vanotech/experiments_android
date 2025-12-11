package com.vanotech.experiments.core.utils.net

import okhttp3.Credentials

@Suppress("unused")
object Http {
    object Auth {
        fun token(scheme: String, token: String?): String = "$scheme ${token.orEmpty()}"

        object Basic {
            const val SCHEME = "Basic"

            fun token(token: String?) = token(SCHEME, token)

            fun token(username: String, password: String) = Credentials.basic(username, password)
        }

        object Bearer {
            const val SCHEME = "Bearer"

            fun token(token: String?) = token(SCHEME, token)
        }
    }

    object Header {
        const val AUTHORIZATION = "Authorization"

        const val USER_AGENT = "User-Agent"
    }
}