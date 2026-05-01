package com.vanotech.experiments.core.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

object DateTimeUtils {

    fun toLocalDateTime(
        instant: Instant,
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): LocalDateTime {
        return instant.toLocalDateTime(timeZone)
    }
}