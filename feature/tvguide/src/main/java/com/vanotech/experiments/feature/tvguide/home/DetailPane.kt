package com.vanotech.experiments.feature.tvguide.home

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.vanotech.experiments.core.ui.AspectRatio
import com.vanotech.experiments.core.ui.BackButton
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailPane(
    viewModel: HomeViewModel,
    isListAndDetailVisible: Boolean,
    onBackPress: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    if (!isListAndDetailVisible) {
                        BackButton(onBackPress = onBackPress)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        val uiState = viewModel.uiState.collectAsState().value
        val listing = uiState.listing.collectAsState(null).value
        listing?.also { listing ->
            DetailContent(
                listing = listing,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun DetailContent(
    listing: Listing,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
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
                val startAt = listing.startAt.toEpochMilli()
                val endAt = listing.startAt.plus(listing.duration).toEpochMilli()
                val dates = DateUtils.formatDateRange(
                    LocalContext.current,
                    startAt,
                    endAt,
                    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_YEAR
                )
                val times = DateUtils.formatDateRange(
                    LocalContext.current,
                    startAt,
                    endAt,
                    DateUtils.FORMAT_SHOW_TIME
                )
                Text(
                    text = dates,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = times,
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
