package com.vanotech.experiments.feature.tvguide.detail

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.vanotech.experiments.core.ui.NavigateBackButton
import com.vanotech.experiments.data.tvguide.schema.ListingType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val program = viewModel.program.collectAsState(null).value
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    program?.also {
                        Text(
                            text = it.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    NavigateBackButton(navController = navController)
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        program?.also { program ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(program.imageUrl)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val icon = when (program.type) {
                        ListingType.MOVIE -> Icons.Default.Movie
                        else -> Icons.Default.Tv
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                    Column {
                        Text(
                            text = program.title,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Text(
                            text = program.channelTitle,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column {
                        val startAt = program.startAt.toEpochMilli()
                        val endAt = program.startAt.plus(program.duration).toEpochMilli()
                        val dates = DateUtils.formatDateRange(
                            LocalContext.current,
                            startAt,
                            endAt,
                            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_YEAR
                        )
                        Text(
                            text = dates,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        val times = DateUtils.formatDateRange(
                            LocalContext.current,
                            startAt,
                            endAt,
                            DateUtils.FORMAT_SHOW_TIME
                        )
                        Text(
                            text = times,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    program.summary?.also {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}
