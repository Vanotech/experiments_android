package com.vanotech.experiments.feature.tvguide.listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanotech.experiments.feature.tvguide.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ListingsSettingsBottomSheet(
    viewModel: ListingsViewModel,
    onDismissRequest: () -> Unit
) {
    val showEpisodes = viewModel.showEpisodes.collectAsState(initial = false)
    val showMovies = viewModel.showMovies.collectAsState(initial = false)
    val startTime = viewModel.startTime.collectAsState(initial = LocalTime.MIN)
    val endTime = viewModel.endTime.collectAsState(initial = LocalTime.MAX)

    ListingsSettingsBottomSheet(
        onDismissRequest = onDismissRequest,
        showEpisodes = showEpisodes.value,
        onShowEpisodesChanged = {
            viewModel.setShowEpisodes(it)
        },
        showMovies = showMovies.value,
        onShowMoviesChanged = {
            viewModel.setShowMovies(it)
        },
        startTime = startTime.value,
        onStartTimeChanged = {
            viewModel.setStartTime(it)
        },
        endTime = endTime.value,
        onEndTimeChanged = {
            viewModel.setEndTime(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingsSettingsBottomSheet(
    onDismissRequest: () -> Unit,
    showEpisodes: Boolean,
    onShowEpisodesChanged: (Boolean) -> Unit,
    showMovies: Boolean,
    onShowMoviesChanged: (Boolean) -> Unit,
    startTime: LocalTime,
    onStartTimeChanged: (LocalTime) -> Unit,
    endTime: LocalTime,
    onEndTimeChanged: (LocalTime) -> Unit,
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var showStartTimePicker by remember { mutableStateOf(false) }
    val startTimePickerState = rememberTimePickerState(
        initialHour = startTime.hour,
        initialMinute = startTime.minute,
        is24Hour = true
    )
    var showEndTimePicker by remember { mutableStateOf(false) }
    val endTimePickerState = rememberTimePickerState(
        initialHour = endTime.hour,
        initialMinute = endTime.minute,
        is24Hour = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.hint_show_episodes),
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = showEpisodes,
                onCheckedChange = onShowEpisodesChanged
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.hint_show_movies),
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = showMovies,
                onCheckedChange = onShowMoviesChanged
            )
        }
        Row(
            modifier = Modifier
                .clickable {
                    showStartTimePicker = true
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.hint_start_time),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = timeFormatter.format(startTime),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Row(
            modifier = Modifier
                .clickable {
                    showEndTimePicker = true
                }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.hint_end_time),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = timeFormatter.format(endTime),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    when {
        showStartTimePicker -> {
            ListingsTimePickerDialog(
                onDismissRequest = {
                    showStartTimePicker = false
                },
                onConfirmRequest = {
                    onStartTimeChanged(
                        LocalTime.of(
                            startTimePickerState.hour,
                            startTimePickerState.minute
                        )
                    )
                },
                state = startTimePickerState
            )
        }

        showEndTimePicker -> {
            ListingsTimePickerDialog(
                onDismissRequest = {
                    showEndTimePicker = false
                },
                onConfirmRequest = {
                    onEndTimeChanged(
                        LocalTime.of(
                            endTimePickerState.hour,
                            endTimePickerState.minute
                        )
                    )
                },
                state = endTimePickerState
            )
        }
    }
}

@Preview
@Composable
fun ListingSettingsBottomSheetPreview() {
    ListingsSettingsBottomSheet(
        onDismissRequest = { },
        showEpisodes = true,
        onShowEpisodesChanged = {},
        showMovies = true,
        onShowMoviesChanged = {},
        startTime = LocalTime.MIN,
        onStartTimeChanged = {},
        endTime = LocalTime.MAX,
        onEndTimeChanged = {}
    )
}
