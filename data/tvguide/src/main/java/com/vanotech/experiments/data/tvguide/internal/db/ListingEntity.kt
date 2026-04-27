package com.vanotech.experiments.data.tvguide.internal.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanotech.experiments.data.tvguide.ListingType
import kotlin.time.Duration
import kotlin.time.Instant

@Entity(tableName = ListingEntity.TABLE_NAME)
data class ListingEntity(
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

    }
}