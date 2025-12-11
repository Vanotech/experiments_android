package com.vanotech.experiments.core.utils.net

@Suppress("unused")
object OAuth {
    object GrantType {
        const val AUTHORIZATION_CODE = "authorization_code"

        const val CLIENT_CREDENTIALS = "client_credentials"

        const val DEVICE_CODE = "urn:ietf:params:oauth:grant-type:device_code"

        const val JWT_BEARER = "urn:ietf:params:oauth:grant-type:jwt-bearer"

        const val PASSWORD = "password"

        const val REFRESH_TOKEN = "refresh_token"

        const val SAML2_BEARER = "urn:ietf:params:oauth:grant-type:saml2-bearer"

        const val TOKEN_EXCHANGE = "urn:ietf:params:oauth:grant-type:token-exchange"

        const val UMA_TICKET = "urn:ietf:params:oauth:grant-type:uma-ticket"
    }
}