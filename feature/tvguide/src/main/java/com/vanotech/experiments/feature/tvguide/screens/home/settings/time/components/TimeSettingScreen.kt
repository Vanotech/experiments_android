package com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components

import android.R
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimeSettingScreen(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimeSettingViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    if (!uiState.isLoading) {
        val state = rememberTimePickerState(
            initialHour = uiState.time.hour,
            initialMinute = uiState.time.minute,
            is24Hour = DateFormat.is24HourFormat(LocalContext.current)
        )
        TimeSettingScreen(
            title = title,
            state = state,
            onDismissRequest = onDismissRequest,
            onConfirmRequest = onConfirmRequest,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeSettingScreen(
    state: TimePickerState,
    title: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = AlertDialogDefaults.shape,
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Column(
            modifier = modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
            TimePicker(state = state)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                TextButton(
                    onClick = {
                        onConfirmRequest(
                            LocalTime(state.hour, state.minute)
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        }
    }
}