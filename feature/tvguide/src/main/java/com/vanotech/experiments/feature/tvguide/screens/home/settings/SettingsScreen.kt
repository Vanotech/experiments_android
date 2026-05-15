package com.vanotech.experiments.feature.tvguide.screens.home.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vanotech.experiments.feature.tvguide.R
import com.vanotech.experiments.feature.tvguide.screens.home.settings.components.SwitchSetting
import com.vanotech.experiments.feature.tvguide.screens.home.settings.components.TextSetting
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

@Composable
internal fun SettingsScreen(
    onEditStartTimeRequest: () -> Unit,
    onEditEndTimeRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = SettingsViewModel.viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    SettingsScreen(
        showEpisodes = uiState.showEpisodes,
        onShowEpisodesChanged = {
            coroutineScope.launch {
                viewModel.setShowEpisodes(it)
            }
        },
        showMovies = uiState.showMovies,
        onShowMoviesChanged = {
            coroutineScope.launch {
                viewModel.setShowMovies(it)
            }
        },
        startTime = uiState.startTime,
        onEditStartTimeRequest = onEditStartTimeRequest,
        endTime = uiState.endTime,
        onEditEndTimeRequest = onEditEndTimeRequest,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    showEpisodes: Boolean,
    onShowEpisodesChanged: (Boolean) -> Unit,
    showMovies: Boolean,
    onShowMoviesChanged: (Boolean) -> Unit,
    startTime: LocalTime,
    onEditStartTimeRequest: () -> Unit,
    endTime: LocalTime,
    onEditEndTimeRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timeFormatter = LocalTime.Format {
        hour()
        char(':')
        minute()
    }

    Column(
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
        TextSetting(
            text = stringResource(R.string.hint_start_time),
            value = timeFormatter.format(startTime),
            onClick = onEditStartTimeRequest
        )
        TextSetting(
            text = stringResource(R.string.hint_end_time),
            value = timeFormatter.format(endTime),
            onClick = onEditEndTimeRequest
        )
    }
}

@Preview
@Composable
fun HomeSettingsBottomSheetPreview() {
    SettingsScreen(
        showEpisodes = true,
        onShowEpisodesChanged = {},
        showMovies = true,
        onShowMoviesChanged = {},
        startTime = LocalTime(0, 0),
        onEditStartTimeRequest = {},
        endTime = LocalTime(23, 59),
        onEditEndTimeRequest = {}
    )
}
