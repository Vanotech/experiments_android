package com.vanotech.experiments.data.tvguide.internal.net

import com.vanotech.experiments.data.tvguide.ListingType
import com.vanotech.experiments.data.tvguide.internal.db.ChannelEntity
import com.vanotech.experiments.data.tvguide.internal.db.ScheduleEntity
import com.vanotech.experiments.data.tvguide.internal.db.SchedulePartial
import com.vanotech.experiments.data.tvguide.internal.net.schema.Channel
import com.vanotech.experiments.data.tvguide.internal.net.schema.Schedule
import com.vanotech.experiments.data.tvguide.internal.net.schema.SingleResponse
import com.vanotech.experiments.data.tvguide.internal.net.schema.Type

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
