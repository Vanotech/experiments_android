package com.vanotech.experiments.feature.media.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.ContentFrame
import androidx.navigation.NavController


@Composable
internal fun ViewScreen(
    navController: NavController,
    viewModel: ViewViewModel = hiltViewModel()
) {
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val lifecycle = lifecycleOwner.lifecycle
    val uiState = viewModel.uiState.collectAsState().value
    uiState.media?.url?.also { url ->
        VideoContent(
            lifecycle = lifecycle,
            lifecycleOwner = lifecycleOwner,
            videoUri = url
        )
    }
}

@Composable
private fun VideoContent(
    lifecycle: Lifecycle,
    lifecycleOwner: LifecycleOwner,
    videoUri: String,
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUri)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.play()
                }

                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ContentFrame(player = exoPlayer)
    }
}