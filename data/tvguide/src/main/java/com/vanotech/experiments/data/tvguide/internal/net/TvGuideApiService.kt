package com.vanotech.experiments.data.tvguide.internal.net

import com.vanotech.experiments.core.utils.DateTimeUtils
import com.vanotech.experiments.data.tvguide.Listing
import com.vanotech.experiments.data.tvguide.internal.net.schema.Channel
import com.vanotech.experiments.data.tvguide.internal.net.schema.SingleResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.TimeZone
import javax.inject.Inject
import kotlin.time.Instant

internal class TvGuideApiService @Inject constructor(
    private val client: HttpClient,
    private val baseUrl: String = BASE_URL
) {
    suspend fun getListings(
        platform: String,
        region: String,
        instant: Instant
    ): List<Listing> {
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
        val body: List<Channel> = response.body()
        return body.flatMap { channel ->
            channel.schedules.map { schedule ->
                schedule.toListing(channel)
            }
        }
    }

    suspend fun getSingle(
        paId: String
    ): Listing {
        val response = client.get("$baseUrl/single") {
            url {
                parameters.append("pa_id", paId)
            }
        }
        val body: SingleResponse = response.body()
        return body.toListing()
    }

    companion object {
        private const val BASE_URL = "https://api-2.tvguide.co.uk"
    }
}