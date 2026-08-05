package com.vanotech.experiments.data.tvguide.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanotech.experiments.data.tvguide.model.ListingType
import kotlin.time.Duration
import kotlin.time.Instant

@Entity(tableName = ScheduleEntity.TABLE_NAME)
data class ScheduleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val type: ListingType,
    val imageUrl: String?,
    val startAt: Instant,
    val duration: Duration,
    val summary: String?,
    val channelId: String?
) {
    companion object {
        const val TABLE_NAME = "schedules"
    }
}