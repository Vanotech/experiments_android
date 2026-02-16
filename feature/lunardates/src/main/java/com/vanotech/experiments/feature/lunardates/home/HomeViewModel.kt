package com.vanotech.experiments.feature.lunardates.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vanotech.experiments.data.lunardates.EventRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    eventRepo: EventRepo
) : ViewModel() {

    val events: Flow<PagingData<EventUiModel>> = run {
        val pagedData = eventRepo.getAllAsPagingData(
            config = PagingConfig(pageSize = 50)
        ).cachedIn(viewModelScope)

        pagedData.map { pagingData ->
            pagingData.map {
                EventUiModel(it)
            }
        }.cachedIn(viewModelScope)
    }
}