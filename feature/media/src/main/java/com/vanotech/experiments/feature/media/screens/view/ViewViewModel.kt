package com.vanotech.experiments.feature.media.screens.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.media.MediaRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel
import org.koin.core.parameter.parametersOf

@KoinViewModel
internal class ViewViewModel(
    @InjectedParam args: ViewRoute,
    private val mediaRepo: MediaRepo
) : ViewModel() {
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

    companion object {
        @Composable
        fun viewModel(args: ViewRoute): ViewViewModel {
            return koinViewModel(
                parameters = { parametersOf(args) }
            )
        }
    }
}