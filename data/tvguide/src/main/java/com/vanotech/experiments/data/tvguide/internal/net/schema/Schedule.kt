package com.vanotech.experiments.data.tvguide.internal.net.schema

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vanotech.experiments.core.utils.annotations.Minutes
import java.time.Duration
import java.time.Instant

@JsonClass(generateAdapter = true)
data class Schedule(
    @field:Json(name = "pa_id") val paId: String,
    val title: String,
    val type: String,
    @field:Json(name = "start_at") val startAt: Instant,
    @field:Minutes val duration: Duration,
    @field:Json(name = "image_url") val imageUrl: String
)
