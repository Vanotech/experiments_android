package com.vanotech.experiments.core.utils.room

import androidx.room.TypeConverter
import java.time.Duration
import java.time.Instant

class TimeConverters {
    @TypeConverter
    fun fromDuration(duration: Duration?): Long? = duration?.toMillis()

    @TypeConverter
    fun toDuration(milliseconds: Long?): Duration? = milliseconds?.let { Duration.ofMillis(it) }

    @TypeConverter
    fun fromInstant(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    fun toInstant(epochMilliseconds: Long?): Instant? =
        epochMilliseconds?.let { Instant.ofEpochMilli(it) }
}