package com.vanotech.experiments.data.tvguide.internal.net

import com.vanotech.experiments.data.tvguide.Listing
import com.vanotech.experiments.data.tvguide.ListingType
import com.vanotech.experiments.data.tvguide.internal.net.schema.Channel
import com.vanotech.experiments.data.tvguide.internal.net.schema.Schedule
import com.vanotech.experiments.data.tvguide.internal.net.schema.SingleResponse
import com.vanotech.experiments.data.tvguide.internal.net.schema.Type

internal fun Schedule.toListing(
    channel: Channel
) = Listing(
    id = paId,
    title = title,
    type = toListingType(type),
    imageUrl = imageUrl,
    startAt = startAt,
    duration = duration,
    channelTitle = channel.title,
    channelLogoUrl = channel.logoUrl,
    summary = null
)

internal fun SingleResponse.toListing(
) = Listing(
    id = paId,
    title = title,
    type = toListingType(type),
    imageUrl = imageUrl,
    startAt = startAt,
    duration = duration,
    channelTitle = channelTitle,
    channelLogoUrl = channelLogoUrl,
    summary = summaryLong
)

internal fun toListingType(type: String): ListingType {
    return when (type) {
        Type.EPISODE -> ListingType.EPISODE
        Type.MOVIE -> ListingType.MOVIE
        else -> ListingType.UNKNOWN
    }
}
