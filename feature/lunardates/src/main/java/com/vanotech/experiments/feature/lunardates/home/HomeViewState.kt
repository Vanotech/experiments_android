package com.vanotech.experiments.feature.lunardates.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class HomeViewState(
    val events: Flow<PagingData<EventUiModel>> = emptyFlow()
)