package com.vanotech.experiments.feature.tvguide.home.settings

import android.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vanotech.experiments.core.ui.TimePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimeSettingDialog(
    state: TimePickerState,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    TimePickerDialog(
        state = state,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmRequest()
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}
