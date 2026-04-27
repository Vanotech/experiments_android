package com.vanotech.experiments.core.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

object TimeUtils {

    fun toLocalDateTime(
        instant: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDateTime {
        return instant.toLocalDateTime(timeZone)
    }

    fun toLocalDateTime(
        epochMillis: Long,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDateTime {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        return toLocalDateTime(instant, timeZone)
    }

    fun toLocalDate(
        instant: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDate {
        val localDateTime = toLocalDateTime(instant, timeZone)
        return localDateTime.date
    }

    fun toLocalDate(
        epochMillis: Long,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDate {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        return toLocalDate(instant, timeZone)
    }

    fun toLocalTime(
        instant: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalTime {
        val localDateTime = toLocalDateTime(instant, timeZone)
        return localDateTime.time
    }

    fun toLocalTime(
        epochMillis: Long,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalTime {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        return toLocalTime(instant, timeZone)
    }
}