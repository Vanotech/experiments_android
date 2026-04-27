package com.vanotech.experiments.data.tvguide.internal.net.schema

import com.vanotech.experiments.core.utils.serialization.DurationInMinutesAsLongSerializer
import com.vanotech.experiments.core.utils.serialization.InstantAsStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant


@Serializable
data class SingleResponse(
    @SerialName("pa_id") val paId: String,
    val title: String,
    val type: String,
    @Serializable(with = InstantAsStringSerializer::class)
    @SerialName("start_at") val startAt: Instant,
    @Serializable(with = DurationInMinutesAsLongSerializer::class)
    val duration: Duration,
    @SerialName("channel_title") val channelTitle: String,
    @SerialName("channel_logo_url") val channelLogoUrl: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("summary_short") val summaryShort: String,
    @SerialName("summary_long") val summaryLong: String,
)