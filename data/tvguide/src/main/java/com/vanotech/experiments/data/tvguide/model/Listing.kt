package com.vanotech.experiments.data.tvguide.model

import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

data class Listing(
    val id: String,
    val title: String,
    val type: ListingType,
    val imageUrl: String? = null,
    val startAt: Instant,
    val duration: Duration,
    val summary: String? = null,
    val channelTitle: String,
    val channelLogoUrl: String? = null
) {
    companion object {
        fun mockData(index: Int = 0): Listing {
            return Listing(
                id = "$index",
                title = "Title $index",
                type = ListingType.UNKNOWN,
                startAt = Clock.System.now(),
                duration = 1.hours,
                channelTitle = "Channel $index"
            )
        }
    }
}