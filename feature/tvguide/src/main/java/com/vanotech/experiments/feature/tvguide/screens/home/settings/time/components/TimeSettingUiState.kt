package com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components

import kotlinx.datetime.LocalTime

internal data class TimeSettingUiState(
    val isLoading: Boolean,
    val time: LocalTime = LocalTime(0, 0),
)