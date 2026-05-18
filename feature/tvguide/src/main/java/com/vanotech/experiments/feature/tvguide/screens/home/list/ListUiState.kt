package com.vanotech.experiments.feature.tvguide.screens.home.list

import kotlinx.datetime.LocalTime

internal data class ListUiState(
    val showEpisodes: Boolean = false,
    val showMovies: Boolean = false,
    val startTime: LocalTime = LocalTime(0, 0),
    val endTime: LocalTime = LocalTime(23, 59)
)