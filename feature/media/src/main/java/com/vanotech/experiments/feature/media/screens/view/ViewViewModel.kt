package com.vanotech.experiments.feature.media.screens.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vanotech.experiments.data.media.MediaRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class ViewViewModel(
    savedStateHandle: SavedStateHandle,
    private val mediaRepo: MediaRepo
) : ViewModel() {
    private val args = savedStateHandle.toRoute<ViewRoute>()
    private val mediaId = args.mediaId

    private val _uiState = MutableStateFlow(ViewUiState())
    val uiState: StateFlow<ViewUiState> = _uiState

    init {
        viewModelScope.launch {
            mediaRepo.get(mediaId)?.also { media ->
                _uiState.update {
                    it.copy(media = media)
                }
            }
        }
    }
}