package com.vanotech.experiments.feature.tvguide.screens.home.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vanotech.experiments.feature.tvguide.R
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeSettingsBottomSheet(
    onDismissRequest: () -> Unit,
    showEpisodes: Boolean,
    onShowEpisodesChanged: (Boolean) -> Unit,
    showMovies: Boolean,
    onShowMoviesChanged: (Boolean) -> Unit,
    startTime: LocalTime,
    onStartTimeChanged: (LocalTime) -> Unit,
    endTime: LocalTime,
    onEndTimeChanged: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val timeFormatter = LocalTime.Format {
        hour()
        char(':')
        minute()
    }
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
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        SwitchSetting(
            text = stringResource(R.string.hint_show_episodes),
            checked = showEpisodes,
            onCheckedChange = onShowEpisodesChanged
        )
        SwitchSetting(
            text = stringResource(R.string.hint_show_movies),
            checked = showMovies,
            onCheckedChange = onShowMoviesChanged
        )
        ValueSetting(
            text = stringResource(R.string.hint_start_time),
            value = timeFormatter.format(startTime),
            onClick = {
                showStartTimePicker = true
            }
        )
        ValueSetting(
            text = stringResource(R.string.hint_end_time),
            value = timeFormatter.format(endTime),
            onClick = {
                showEndTimePicker = true
            }
        )
    }

    when {
        showStartTimePicker -> {
            TimeSettingDialog(
                state = startTimePickerState,
                title = stringResource(R.string.hint_start_time),
                onDismissRequest = {
                    showStartTimePicker = false
                },
                onConfirmRequest = {
                    onStartTimeChanged(
                        LocalTime(
                            startTimePickerState.hour,
                            startTimePickerState.minute
                        )
                    )
                },
            )
        }

        showEndTimePicker -> {
            TimeSettingDialog(
                state = endTimePickerState,
                title = stringResource(R.string.hint_end_time),
                onDismissRequest = {
                    showEndTimePicker = false
                },
                onConfirmRequest = {
                    onEndTimeChanged(
                        LocalTime(
                            endTimePickerState.hour,
                            endTimePickerState.minute
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeSettingsBottomSheetPreview() {
    HomeSettingsBottomSheet(
        onDismissRequest = { },
        showEpisodes = true,
        onShowEpisodesChanged = {},
        showMovies = true,
        onShowMoviesChanged = {},
        startTime = LocalTime(0, 0),
        onStartTimeChanged = {},
        endTime = LocalTime(23,59),
        onEndTimeChanged = {}
    )
}
