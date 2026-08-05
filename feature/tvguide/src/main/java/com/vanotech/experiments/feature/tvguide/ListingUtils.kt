package com.vanotech.experiments.feature.tvguide

import android.content.Context
import android.text.format.DateUtils
import com.vanotech.experiments.data.tvguide.model.Listing

object ListingUtils {

    fun formatDates(context: Context, startMillis: Long, endMillis: Long): String {
        return DateUtils.formatDateRange(
            context,
            startMillis,
            endMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY
        )
    }

    fun formatDates(context: Context, listing: Listing): String {
        val startMillis = listing.startAt.toEpochMilliseconds()
        val endMillis = startMillis + listing.duration.inWholeMilliseconds
        return formatDates(context, startMillis, endMillis)
    }

    fun formatTimes(context: Context, startMillis: Long, endMillis: Long): String {
        return DateUtils.formatDateRange(
            context,
            startMillis,
            endMillis,
            DateUtils.FORMAT_SHOW_TIME
        )
    }

    fun formatTimes(context: Context, listing: Listing): String {
        val startMillis = listing.startAt.toEpochMilliseconds()
        val endMillis = startMillis + listing.duration.inWholeMilliseconds
        return formatTimes(context, startMillis, endMillis)
    }
}