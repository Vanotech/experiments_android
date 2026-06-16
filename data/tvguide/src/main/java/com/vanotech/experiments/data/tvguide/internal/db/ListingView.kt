package com.vanotech.experiments.data.tvguide.internal.db

import androidx.room.DatabaseView
import com.vanotech.experiments.data.tvguide.ListingType
import kotlin.time.Duration
import kotlin.time.Instant

@DatabaseView(
    """
SELECT 
s.id, 
s.title,
s.type,
s.imageUrl,
s.startAt,
s.duration,
s.summary,
c.title AS channelTitle,
c.logoUrl AS channelLogoUrl
FROM ${ScheduleEntity.TABLE_NAME} AS s
INNER JOIN ${ChannelEntity.TABLE_NAME} AS c
ON s.channelId = c.id
    """,
    viewName = ListingView.TABLE_NAME
)
data class ListingView(
    val id: String,
    val title: String,
    val type: ListingType,
    val imageUrl: String?,
    val startAt: Instant,
    val duration: Duration,
    val summary: String?,
    val channelTitle: String,
    val channelLogoUrl: String?
) {
    companion object {
        const val TABLE_NAME = "listings"
    }
}