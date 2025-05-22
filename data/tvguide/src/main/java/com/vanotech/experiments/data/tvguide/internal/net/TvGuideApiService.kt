package com.vanotech.experiments.data.tvguide.internal.net

import com.vanotech.experiments.data.tvguide.internal.net.schema.Type
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType
import java.time.LocalDate
import javax.inject.Inject

internal class TvGuideApiService @Inject constructor(
    private val api: TvGuideApi
) {
    suspend fun getListings(
        platform: String,
        region: String,
        date: LocalDate,
        hour: Int,
    ): List<Listing> {
        val channels = api.getListings(
            platform = platform,
            region = region,
            date = date.toString(),
            hour = hour,
            details = false
        )
        return channels.flatMap { channel ->
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

    suspend fun getProgram(
        paId: String
    ): Listing {
        val program = api.getProgram(paId)
        return Listing(
            id = program.paId,
            title = program.title,
            type = programTypeOf(program.type),
            imageUrl = program.imageUrl,
            startAt = program.startAt,
            duration = program.duration,
            channelTitle = program.channelTitle,
            channelLogoUrl = program.channelLogoUrl,
            summary = program.summaryLong
        )
    }

    companion object {
        private fun programTypeOf(type: String): ListingType {
            return when (type) {
                Type.EPISODE -> ListingType.EPISODE
                Type.MOVIE -> ListingType.MOVIE
                else -> ListingType.UNKNOWN
            }
        }
    }
}