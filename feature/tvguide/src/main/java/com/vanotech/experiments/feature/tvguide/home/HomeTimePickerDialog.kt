package com.vanotech.experiments.feature.tvguide.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vanotech.experiments.core.ui.TimePickerDialog

@ExperimentalMaterial3Api
@Composable
internal fun HomeTimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    state: TimePickerState
) {
    TimePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmRequest()
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(android.R.string.cancel))
            }
        }
    ) {
        TimePicker(state = state)
    }
}
