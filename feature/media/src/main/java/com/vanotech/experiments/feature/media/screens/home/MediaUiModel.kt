package com.vanotech.experiments.feature.media.screens.home

import androidx.compose.runtime.Immutable
import com.vanotech.experiments.data.media.Media

@Immutable
internal data class MediaUiModel(
    private val media: Media
) {
    val id = media.id
    val url = media.url
    val title = media.title
}
