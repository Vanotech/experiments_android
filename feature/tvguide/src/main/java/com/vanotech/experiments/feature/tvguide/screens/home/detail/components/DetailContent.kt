package com.vanotech.experiments.feature.tvguide.screens.home.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.vanotech.experiments.core.ui.AspectRatio
import com.vanotech.experiments.data.tvguide.model.Listing
import com.vanotech.experiments.data.tvguide.model.ListingType
import com.vanotech.experiments.feature.tvguide.ListingUtils

@Composable
internal fun DetailContent(
    listing: Listing,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(listing.imageUrl)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(AspectRatio.WIDE_SCREEN),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val icon = when (listing.type) {
                ListingType.MOVIE -> Icons.Default.Movie
                else -> Icons.Default.Tv
            }
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Column {
                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = listing.channelTitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column {
                val dateRange = ListingUtils.formatDates(LocalContext.current, listing)
                val timeRange = ListingUtils.formatTimes(LocalContext.current, listing)
                Text(
                    text = dateRange,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = timeRange,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            listing.summary?.also {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
