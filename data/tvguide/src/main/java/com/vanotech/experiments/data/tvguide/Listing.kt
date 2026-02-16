package com.vanotech.experiments.data.tvguide

import java.time.Duration
import java.time.Instant

data class Listing(
    val id: String,
    val title: String,
    val type: ListingType,
    val imageUrl: String?,
    val startAt: Instant,
    val duration: Duration,
    val channelTitle: String,
    val channelLogoUrl: String?,
    val summary: String?
) {
    companion object {
        fun mockData(index: Int): Listing {
            return Listing(
                id = "$index",
                title = "Title $index",
                type = ListingType.UNKNOWN,
                imageUrl = null,
                startAt = Instant.now(),
                duration = Duration.ofHours(1),
                channelTitle = "Channel $index",
                channelLogoUrl = null,
                summary = null
            )
        }
    }
}