package com.vanotech.experiments.feature.media.screens.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vanotech.experiments.feature.media.screens.view.components.VideoContent


@Composable
internal fun ViewScreen(
    args: ViewRoute,
    modifier: Modifier = Modifier,
    viewModel: ViewViewModel = ViewViewModel.viewModel(args)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ViewScreen(
        url = uiState.media?.url,
        modifier = modifier
    )
}

@Composable
private fun ViewScreen(
    url: String?,
    modifier: Modifier = Modifier
) {
    url?.also { url ->
        val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)

        VideoContent(
            lifecycleOwner = lifecycleOwner,
            url = url,
            modifier = modifier
        )
    }
}
