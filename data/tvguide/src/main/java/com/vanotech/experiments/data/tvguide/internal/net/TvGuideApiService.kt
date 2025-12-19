package com.vanotech.experiments.data.tvguide.internal.net

import com.vanotech.experiments.data.tvguide.internal.net.schema.Channel
import com.vanotech.experiments.data.tvguide.internal.net.schema.Schedule
import com.vanotech.experiments.data.tvguide.internal.net.schema.SingleResponse
import com.vanotech.experiments.data.tvguide.internal.net.schema.Type
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

internal class TvGuideApiService @Inject constructor(
    private val client: HttpClient,
    private val baseUrl: String = BASE_URL
) {
    suspend fun getListings(
        platform: String,
        region: String,
        instant: Instant
    ): List<Listing> {
        val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        val date = dateTime.toLocalDate()
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
                Listing(
                    id = schedule.paId,
                    title = schedule.title,
                    type = programTypeOf(schedule.type),
                    imageUrl = schedule.imageUrl,
                    startAt = schedule.startAt,
                    duration = schedule.duration,
                    channelTitle = channel.title,
                    channelLogoUrl = channel.logoUrl,
                    summary = null
                )
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
        return Listing(
            id = body.paId,
            title = body.title,
            type = programTypeOf(body.type),
            imageUrl = body.imageUrl,
            startAt = body.startAt,
            duration = body.duration,
            channelTitle = body.channelTitle,
            channelLogoUrl = body.channelLogoUrl,
            summary = body.summaryLong
        )
    }

    companion object {
        private const val BASE_URL = "https://api-2.tvguide.co.uk"

        private fun programTypeOf(type: String): ListingType {
            return when (type) {
                Type.EPISODE -> ListingType.EPISODE
                Type.MOVIE -> ListingType.MOVIE
                else -> ListingType.UNKNOWN
            }
        }
    }
}