package com.vanotech.experiments.feature.tvguide.screens.home

import com.vanotech.experiments.data.tvguide.Listing
import java.time.LocalTime

internal data class HomeUiState(
    val listing: Listing? = null,
    val showEpisodes: Boolean = false,
    val showMovies: Boolean = false,
    val startTime: LocalTime = LocalTime.MIDNIGHT,
    val endTime: LocalTime = LocalTime.MIDNIGHT
)