package com.vanotech.experiments.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.vanotech.experiments.core.ui.R

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.action_navigate_back)
        )
    }
}

@Composable
fun BackButton(navController: NavController) {
    if (navController.previousBackStackEntry != null) {
        BackButton {
            navController.popBackStack()
        }
    }
}