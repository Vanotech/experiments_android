package com.vanotech.experiments.feature.tvguide.screens.home.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.tvguide.usecase.GetEndTimeSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.GetShowEpisodesSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.GetShowMoviesSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.GetStartTimeSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.SetShowEpisodesSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.SetShowMoviesSettingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class SettingsViewModel(
    getShowEpisodesSettingUseCase: GetShowEpisodesSettingUseCase,
    getShowMoviesSettingUseCase: GetShowMoviesSettingUseCase,
    getStartTimeSettingUseCase: GetStartTimeSettingUseCase,
    getEndTimeSettingUseCase: GetEndTimeSettingUseCase,
    private val setShowEpisodesSettingUseCase: SetShowEpisodesSettingUseCase,
    private val setShowMoviesSettingUseCase: SetShowMoviesSettingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            getShowEpisodesSettingUseCase.flow.collectLatest { showEpisodes ->
                _uiState.update { it.copy(showEpisodes = showEpisodes) }
            }
        }
        viewModelScope.launch {
            getShowMoviesSettingUseCase.flow.collectLatest { showMovies ->
                _uiState.update { it.copy(showMovies = showMovies) }
            }
        }
        viewModelScope.launch {
            getStartTimeSettingUseCase.flow.collectLatest { startTime ->
                _uiState.update { it.copy(startTime = startTime) }
            }
        }
        viewModelScope.launch {
            getEndTimeSettingUseCase.flow.collectLatest { endTime ->
                _uiState.update { it.copy(endTime = endTime) }
            }
        }
    }

    suspend fun setShowEpisodes(value: Boolean) {
        setShowEpisodesSettingUseCase(value)
    }

    suspend fun setShowMovies(value: Boolean) {
        setShowMoviesSettingUseCase(value)
    }

    companion object {
        @Composable
        fun viewModel(): SettingsViewModel {
            return koinViewModel()
        }
    }
}