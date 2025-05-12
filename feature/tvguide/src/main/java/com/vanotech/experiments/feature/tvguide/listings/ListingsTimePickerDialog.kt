package com.vanotech.experiments.feature.tvguide.listings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun ListingsTimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    state: TimePickerState
) {
    com.vanotech.experiments.core.ui.TimePickerDialog(
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
