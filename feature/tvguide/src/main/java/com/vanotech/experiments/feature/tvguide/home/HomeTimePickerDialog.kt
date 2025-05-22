package com.vanotech.experiments.feature.tvguide.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
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
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = "Cancel")
            }
        }
    ) {
        TimePicker(state = state)
    }
}
