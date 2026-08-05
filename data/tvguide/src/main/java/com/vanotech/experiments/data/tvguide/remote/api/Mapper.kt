package com.vanotech.experiments.data.tvguide.remote.api

import com.vanotech.experiments.data.tvguide.local.db.model.ChannelEntity
import com.vanotech.experiments.data.tvguide.local.db.model.ScheduleEntity
import com.vanotech.experiments.data.tvguide.local.db.model.SchedulePartial
import com.vanotech.experiments.data.tvguide.model.ListingType
import com.vanotech.experiments.data.tvguide.remote.api.model.Channel
import com.vanotech.experiments.data.tvguide.remote.api.model.Schedule
import com.vanotech.experiments.data.tvguide.remote.api.model.SingleResponse
import com.vanotech.experiments.data.tvguide.remote.api.model.Type

internal fun Channel.toChannelEntity() = ChannelEntity(
    id = paId,
    title = title,
    logoUrl = logoUrl
)

internal fun Schedule.toScheduleEntity(
    channel: Channel
) = ScheduleEntity(
    id = paId,
    title = title,
    type = toListingType(type),
    imageUrl = imageUrl,
    startAt = startAt,
    duration = duration,
    summary = null,
    channelId = channel.paId
)

internal fun SingleResponse.toSchedulePartial() = SchedulePartial(
    id = paId,
    title = title,
    type = toListingType(type),
    imageUrl = imageUrl,
    startAt = startAt,
    duration = duration,
    summary = summaryLong
)

internal fun toListingType(type: String): ListingType {
    return when (type) {
        Type.EPISODE -> ListingType.EPISODE
        Type.MOVIE -> ListingType.MOVIE
        else -> ListingType.UNKNOWN
    }
}
