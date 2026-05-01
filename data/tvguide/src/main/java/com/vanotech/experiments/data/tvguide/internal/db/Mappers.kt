package com.vanotech.experiments.data.tvguide.internal.db

import com.vanotech.experiments.data.tvguide.Listing

internal fun Listing.toListingEntity() = ListingEntity(
    id = id,
    title = title,
    type = type,
    imageUrl = imageUrl,
    startAt = startAt,
    duration = duration,
    channelTitle = channelTitle,
    channelLogoUrl = channelLogoUrl,
    summary = summary
)

internal fun ListingEntity.toListing() = Listing(
    id = id,
    title = title,
    type = type,
    imageUrl = imageUrl,
    startAt = startAt,
    duration = duration,
    channelTitle = channelTitle,
    channelLogoUrl = channelLogoUrl,
    summary = summary
)

