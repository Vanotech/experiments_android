package com.vanotech.experiments.data.tvguide.internal.db

import com.vanotech.experiments.data.tvguide.ListingType
import kotlin.time.Duration
import kotlin.time.Instant

data class SchedulePartial(
    val id: String,
    val title: String,
    val type: ListingType,
    val imageUrl: String?,
    val startAt: Instant,
    val duration: Duration,
    val summary: String?,
)