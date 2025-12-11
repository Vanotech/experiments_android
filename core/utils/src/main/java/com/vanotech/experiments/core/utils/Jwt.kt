package com.vanotech.experiments.core.utils

import java.nio.charset.Charset
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
        header = decode(decoder, parts[0], charset)
        payload = decode(decoder, parts[1], charset)
    }

    private fun decode(decoder: Base64.Decoder, source: String, charset: Charset): String {
        val encodedBytes = source.toByteArray(charset)
        val decodedBytes = decoder.decode(encodedBytes)
        return decodedBytes.toString(charset)
    }
}