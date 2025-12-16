package com.vanotech.experiments.data.tvguide.internal.net.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    @SerialName("pa_id") val paId: String,
    val title: String,
    @SerialName("logo_url") val logoUrl: String,
    val schedules: List<Schedule>
)
