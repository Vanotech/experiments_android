package com.vanotech.experiments.feature.lunardates.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vanotech.experiments.data.lunardates.EventRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    eventRepo: EventRepo
) : ViewModel() {
    private val pager = Pager(
        config = PagingConfig(pageSize = 50)
    ) {
        eventRepo.getAllAsPagingSource()
    }

    private val pagingDataFlow = pager.flow.cachedIn(viewModelScope)

    val items = pagingDataFlow.cachedIn(viewModelScope)
}