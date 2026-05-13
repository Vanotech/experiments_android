package com.vanotech.experiments.feature.media.screens.view

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal data class ViewRoute(
    val mediaId: Int
) : NavKey