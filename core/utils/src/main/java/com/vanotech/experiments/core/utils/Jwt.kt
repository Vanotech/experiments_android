package com.vanotech.experiments.core.utils

import java.nio.charset.Charset
import kotlin.io.encoding.Base64


class Jwt(
    val token: String
) {
    val header: String
    val payload: String

    init {
        val decoder = Base64.UrlSafe
        val parts = token.split('.')
        header = decode(decoder, parts[0])
        payload = decode(decoder, parts[1])
    }

    private fun decode(
        decoder: Base64,
        source: String,
        charset: Charset = Charsets.UTF_8
    ): String {
        val encodedBytes = source.toByteArray(charset)
        val decodedBytes = decoder.decode(encodedBytes)
        return decodedBytes.toString(charset)
    }
}