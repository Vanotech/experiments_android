package com.vanotech.experiments.feature.tvguide.screens.home.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.tvguide.ListingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class SettingsViewModel(
    private val listingRepo: ListingRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            listingRepo.showEpisodes.collectLatest { showEpisodes ->
                _uiState.update { it.copy(showEpisodes = showEpisodes) }
            }
        }
        viewModelScope.launch {
            listingRepo.showMovies.collectLatest { showMovies ->
                _uiState.update { it.copy(showMovies = showMovies) }
            }
        }
        viewModelScope.launch {
            listingRepo.startTime.collectLatest { startTime ->
                _uiState.update { it.copy(startTime = startTime) }
            }
        }
        viewModelScope.launch {
            listingRepo.endTime.collectLatest { endTime ->
                _uiState.update { it.copy(endTime = endTime) }
            }
        }
    }

    suspend fun setShowEpisodes(value: Boolean) {
        listingRepo.setShowEpisodes(value)
    }

    suspend fun setShowMovies(value: Boolean) {
        listingRepo.setShowMovies(value)
    }

    companion object {
        @Composable
        fun viewModel(): SettingsViewModel {
            return koinViewModel()
        }
    }
}