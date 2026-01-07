package com.vanotech.experiments.feature.media.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.vanotech.experiments.data.media.MediaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    mediaRepo: MediaRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        val pagingDataFlow = mediaRepo.getAllAsPagingData().cachedIn(viewModelScope)

        val items = pagingDataFlow.map { pagingData ->
            pagingData.map {
                MediaUiModel(it)
            }
        }.cachedIn(viewModelScope)

        _uiState.update {
            it.copy(media = items)
        }
    }
}