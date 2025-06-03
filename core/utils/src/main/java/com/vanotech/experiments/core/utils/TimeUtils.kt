package com.vanotech.experiments.core.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object TimeUtils {

    fun toLocalDate(
        instant: Instant,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): LocalDate {
        val zonedDateTime = toZonedDateTime(instant, zoneId)
        return zonedDateTime.toLocalDate()
    }

    fun toLocalDate(
        milliseconds: Long,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): LocalDate {
        val instant = Instant.ofEpochMilli(milliseconds)
        return toLocalDate(instant, zoneId)
    }

    fun toLocalTime(
        instant: Instant,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): LocalTime {
        val zonedDateTime = toZonedDateTime(instant, zoneId)
        return zonedDateTime.toLocalTime()
    }

    fun toLocalTime(
        milliseconds: Long,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): LocalTime {
        val instant = Instant.ofEpochMilli(milliseconds)
        return toLocalTime(instant, zoneId)
    }

    fun toZonedDateTime(
        instant: Instant,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): ZonedDateTime {
        return ZonedDateTime.ofInstant(instant, zoneId)
    }

    fun toZonedDateTime(
        milliseconds: Long,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): ZonedDateTime {
        val instant = Instant.ofEpochMilli(milliseconds)
        return toZonedDateTime(instant, zoneId)
    }
}