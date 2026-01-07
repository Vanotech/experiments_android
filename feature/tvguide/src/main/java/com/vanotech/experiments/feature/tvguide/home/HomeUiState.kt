package com.vanotech.experiments.feature.tvguide.home

import androidx.paging.PagingData
import com.vanotech.experiments.data.tvguide.schema.Listing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class HomeUiState(
    val listings: Flow<PagingData<ListingUiModel>> = emptyFlow(),
    val listing: Flow<Listing?> = emptyFlow(),
    val isRefreshing: Boolean = false
)