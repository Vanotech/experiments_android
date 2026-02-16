package com.vanotech.experiments.feature.lunardates.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vanotech.experiments.core.utils.NamedValue
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo
import com.vanotech.experiments.data.lunardates.usecases.DeleteEventUseCase
import com.vanotech.experiments.data.lunardates.usecases.UpsertEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val eventRepo: EventRepo
) : ViewModel() {
    private val args = savedStateHandle.toRoute<EditRoute>()
    private val eventId = args.eventId
    private val _uiState = let {
        val daysOfMonth = List(30) {
            NamedValue(
                "${it + 1}",
                it + 1
            )
        }

        val months = List(13) {
            NamedValue("${it + 1}", it)
        }

        MutableStateFlow(
            EditUiState(
                initialized = false,
                createOnly = args.createOnly,
                title = "",
                daysOfMonth = daysOfMonth,
                dayOfMonth = daysOfMonth.first(),
                months = months,
                month = months.first()
            )
        )
    }
    val uiState: StateFlow<EditUiState> = _uiState

    init {
        viewModelScope.launch {
            eventRepo.get(eventId)?.also { event ->
                _uiState.update {
                    it.copy(
                        title = event.title,
                        dayOfMonth = it.daysOfMonth.first { it.value == event.dayOfMonth },
                        month = it.months.first { it.value == event.month }
                    )
                }
            }
            _uiState.update { it.copy(initialized = true) }
        }
    }

    fun updateTitle(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun updateDayOfMonth(value: NamedValue<Int>) {
        _uiState.update { it.copy(dayOfMonth = value) }
    }

    fun updateMonth(value: NamedValue<Int>) {
        _uiState.update { it.copy(month = value) }
    }

    fun updateEvent() {
        viewModelScope.launch {
            val upsertEventUseCase = UpsertEventUseCase(eventRepo)
            upsertEventUseCase.execute(
                Event(
                    id = eventId,
                    title = _uiState.value.title,
                    dayOfMonth = _uiState.value.dayOfMonth.value,
                    month = _uiState.value.month.value
                )
            )
        }
    }

    fun deleteEvent() {
        viewModelScope.launch {
            val deleteEventUseCase = DeleteEventUseCase(eventRepo)
            deleteEventUseCase.execute(
                Event(
                    id = eventId,
                    title = "",
                    dayOfMonth = 0,
                    month = 0
                )
            )
        }
    }
}