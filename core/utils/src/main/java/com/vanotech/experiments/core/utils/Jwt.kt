package com.vanotech.experiments.core.utils

import java.nio.charset.StandardCharsets
import java.util.Base64


class Jwt(
    val token: String
) {
    val header: String
    val payload: String

    init {
        val charset = StandardCharsets.UTF_8
        val decoder = Base64.getUrlDecoder()
        val parts = token.split('.')
        header = String(decoder.decode(parts[0].toByteArray(charset)), charset)
        payload = String(decoder.decode(parts[1].toByteArray(charset)), charset)
    }
}