package com.vanotech.experiments.feature.media.screens.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.ContentFrame
import org.koin.androidx.compose.koinViewModel


@Composable
internal fun ViewScreen(
    args: ViewRoute,
    viewModel: ViewViewModel = ViewViewModel.viewModel(args)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ViewScreen(
        videoUri = uiState.media?.url
    )
}

@Composable
private fun ViewScreen(
    videoUri: String?
) {
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    videoUri?.also { url ->
        VideoContent(
            lifecycleOwner = lifecycleOwner,
            videoUri = url
        )
    }
}

@Composable
private fun VideoContent(
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
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ContentFrame(
        player = exoPlayer,
        modifier = Modifier.fillMaxSize()
    )
}