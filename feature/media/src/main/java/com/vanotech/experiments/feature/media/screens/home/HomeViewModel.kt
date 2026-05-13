package com.vanotech.experiments.feature.media.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vanotech.experiments.data.media.MediaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
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

    companion object {
        @Composable
        fun viewModel(): HomeViewModel {
            return koinViewModel()
        }
    }
}