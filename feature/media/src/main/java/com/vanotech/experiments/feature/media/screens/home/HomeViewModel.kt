package com.vanotech.experiments.feature.media.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vanotech.experiments.data.media.MediaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    mediaRepo: MediaRepo
) : ViewModel() {

    val media: Flow<PagingData<MediaUiModel>> = run {
        val pagedData = mediaRepo.getAllAsPagingData().cachedIn(viewModelScope)

        pagedData.map { pagingData ->
            pagingData.map {
                MediaUiModel(it)
            }
        }.cachedIn(viewModelScope)
    }
}