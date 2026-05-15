package com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

internal abstract class TimeSettingViewModel(
    private val time: Flow<LocalTime>,
    private val setter: suspend (LocalTime) -> Unit
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimeSettingUiState(isLoading = true))
    val uiState: StateFlow<TimeSettingUiState> = _uiState

    init {
        viewModelScope.launch {
            time.collectLatest { time ->
                _uiState.update { it.copy(time = time, isLoading = false) }
            }
        }
    }

    suspend fun setTime(value: LocalTime) {
        setter(value)
    }
}