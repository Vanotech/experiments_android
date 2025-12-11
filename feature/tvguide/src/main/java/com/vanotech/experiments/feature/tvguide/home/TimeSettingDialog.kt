package com.vanotech.experiments.feature.tvguide.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vanotech.experiments.core.ui.TimePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSettingDialog(
    state: TimePickerState,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    TimePickerDialog(
        state = state,
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
    )
}
