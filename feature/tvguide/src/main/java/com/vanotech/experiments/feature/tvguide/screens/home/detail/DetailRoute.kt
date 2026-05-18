package com.vanotech.experiments.feature.tvguide.screens.home.detail

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal data class DetailRoute(
    val listingId: String
): NavKey