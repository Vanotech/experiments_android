package com.vanotech.experiments.data.tvguide.remote.api

import com.vanotech.experiments.core.utils.DateTimeUtils
import com.vanotech.experiments.data.tvguide.remote.api.model.Channel
import com.vanotech.experiments.data.tvguide.remote.api.model.SingleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

internal class TvGuideApiService(
    private val client: HttpClient,
    private val baseUrl: String = BASE_URL
) {
    suspend fun fetchListings(
        platform: String,
        region: String,
        instant: Instant
    ): List<Channel> {
        val dateTime = DateTimeUtils.toLocalDateTime(instant, TimeZone.UTC)
        val date = dateTime.date
        val hour = dateTime.hour
        val details = false
        val response = client.get("$baseUrl/listings") {
            url {
                parameters.append("platform", platform)
                parameters.append("region", region)
                parameters.append("date", date.toString())
                parameters.append("hour", hour.toString())
                parameters.append("details", details.toString())
            }
        }
        return response.body()
    }

    suspend fun fetchSingle(
        paId: String
    ): SingleResponse {
        val response = client.get("$baseUrl/single") {
            url {
                parameters.append("pa_id", paId)
            }
        }
        return response.body()
    }

    companion object {
        private const val BASE_URL = "https://api-2.tvguide.co.uk"
    }
}