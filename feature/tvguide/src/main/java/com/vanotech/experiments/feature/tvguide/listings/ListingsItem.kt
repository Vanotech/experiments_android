package com.vanotech.experiments.feature.tvguide.listings

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType
import com.vanotech.experiments.feature.tvguide.program.ProgramRoute

@Composable
fun ListingsItem(
    listing: Listing,
    navController: NavController
) {
    Card(
        onClick = {
            navController.navigate(ProgramRoute(listing.id))
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(listing.imageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            val icon = when (listing.type) {
                ListingType.MOVIE -> Icons.Default.Movie
                else -> Icons.Default.Tv
            }
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = listing.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = listing.channelTitle,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val startAt = listing.startAt.toEpochMilli()
            val endAt = listing.startAt.plus(listing.duration).toEpochMilli()
            val times = DateUtils.formatDateRange(
                LocalContext.current,
                startAt,
                endAt,
                DateUtils.FORMAT_SHOW_TIME
            )
            Text(
                text = times,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun ListingItemPreview() {
    val listing = Listing.mockData(0)
    val navController = rememberNavController()
    ListingsItem(listing = listing, navController = navController)
}