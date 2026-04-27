package com.vanotech.experiments.core.utils.room

import androidx.room.TypeConverter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant

class TimeConverters {
    @TypeConverter
    fun fromDuration(duration: Duration?): Long? = duration?.inWholeMilliseconds

    @TypeConverter
    fun toDuration(milliseconds: Long?): Duration? = milliseconds?.milliseconds

    @TypeConverter
    fun fromInstant(instant: Instant?): Long? = instant?.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(epochMillis: Long?): Instant? = epochMillis?.let {
        Instant.fromEpochMilliseconds(it)
    }
}