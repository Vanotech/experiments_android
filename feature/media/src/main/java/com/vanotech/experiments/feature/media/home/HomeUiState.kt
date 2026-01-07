package com.vanotech.experiments.feature.media.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class HomeUiState(
    val media: Flow<PagingData<MediaUiModel>> = emptyFlow()
)