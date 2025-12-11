package com.vanotech.experiments.feature.tvguide.home

import android.content.Context
import android.text.format.DateUtils
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType

internal data class ListingUiModel(
    private val listing: Listing
) {
    val id = listing.id

    val icon = when (listing.type) {
        ListingType.MOVIE -> Icons.Default.Movie
        else -> Icons.Default.Tv
    }

    val imageUrl = listing.imageUrl

    val title = listing.title

    val channelTitle = listing.channelTitle

    fun times(context: Context): String {
        val startAt = listing.startAt.toEpochMilli()
        val endAt = listing.startAt.plus(listing.duration).toEpochMilli()
        return DateUtils.formatDateRange(
            context,
            startAt,
            endAt,
            DateUtils.FORMAT_SHOW_TIME
        )
    }
}
