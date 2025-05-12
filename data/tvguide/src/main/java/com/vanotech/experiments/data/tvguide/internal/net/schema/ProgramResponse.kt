package com.vanotech.experiments.data.tvguide.internal.net.schema

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Duration
import java.time.Instant

@JsonClass(generateAdapter = true)
data class ProgramResponse(
    @Json(name = "pa_id") val paId: String,
    val title: String,
    val type: String,
    @Json(name = "start_at") val startAt: Instant,
    @com.vanotech.experiments.core.utils.annotations.Minutes val duration: Duration,
    @Json(name = "channel_title") val channelTitle: String,
    @Json(name = "channel_logo_url") val channelLogoUrl: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "summary_short") val summaryShort: String,
    @Json(name = "summary_long") val summaryLong: String,
)