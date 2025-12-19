package com.vanotech.experiments.core.utils.net

import java.nio.charset.Charset
import kotlin.io.encoding.Base64
import kotlin.text.Charsets.ISO_8859_1

@Suppress("unused")
object Http {
    object Auth {
        fun token(scheme: String, token: String?): String = "$scheme ${token.orEmpty()}"

        object Basic {
            const val SCHEME = "Basic"

            fun token(token: String?) = token(SCHEME, token)

            fun token(
                username: String,
                password: String,
                charset: Charset = ISO_8859_1,
            ): String {
                val encoder = Base64.Default
                val source = "$username:$password".toByteArray(charset)
                val token = encoder.encode(source)
                return token(token)
            }
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