package com.vanotech.experiments.feature.media.home

import androidx.navigation.NavController
import com.vanotech.experiments.data.media.Media
import com.vanotech.experiments.feature.media.MediaNavGraph

internal data class MediaUiModel(
    private val media: Media
) {
    val id = media.id
    val url = media.url
    val title = media.title

    fun navigate(navController: NavController) {
        MediaNavGraph.navigateToView(navController, media.id)
    }
}
