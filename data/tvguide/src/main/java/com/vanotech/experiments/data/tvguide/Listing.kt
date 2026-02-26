package com.vanotech.experiments.data.tvguide

import java.time.Duration
import java.time.Instant

data class Listing(
    val id: String,
    val title: String,
    val type: ListingType,
    val imageUrl: String? = null,
    val startAt: Instant,
    val duration: Duration,
    val channelTitle: String,
    val channelLogoUrl: String? = null,
    val summary: String? = null
) {
    companion object {
        fun mockData(index: Int = 0): Listing {
            return Listing(
                id = "$index",
                title = "Title $index",
                type = ListingType.UNKNOWN,
                startAt = Instant.now(),
                duration = Duration.ofHours(1),
                channelTitle = "Channel $index"
            )
        }
    }
}