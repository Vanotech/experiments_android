package com.vanotech.experiments.feature.tvguide.screens.home.settings.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.vanotech.experiments.feature.tvguide.R
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components.TimeSettingScreen
import kotlinx.coroutines.launch

@Composable
internal fun StartTimeSettingScreen(
    onDismissRequest: () -> Unit,
    viewModel: StartTimeSettingViewModel = StartTimeSettingViewModel.viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    TimeSettingScreen(
        title = stringResource(R.string.hint_start_time),
        onDismissRequest = onDismissRequest,
        onConfirmRequest = {
            coroutineScope.launch {
                viewModel.setTime(it)
                onDismissRequest()
            }
        },
        viewModel = viewModel
    )
}
