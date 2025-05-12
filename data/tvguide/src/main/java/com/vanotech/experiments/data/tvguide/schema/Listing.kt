package com.vanotech.experiments.data.tvguide.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.Instant


@Entity(tableName = Listing.TABLE_NAME)
data class Listing(
    @PrimaryKey val id: String,
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
        const val TABLE_NAME = "listings"

        fun mockData(index: Int): Listing {
            return Listing(
                id = "$index",
                title = "Program $index",
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