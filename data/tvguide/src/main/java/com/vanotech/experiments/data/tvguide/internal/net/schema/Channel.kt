package com.vanotech.experiments.data.tvguide.internal.net.schema

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Channel(
    @field:Json(name = "pa_id") val paId: String,
    val title: String,
    @field:Json(name = "logo_url") val logoUrl: String,
    val schedules: List<Schedule>
)
