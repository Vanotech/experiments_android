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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val eventRepo: EventRepo
) : ViewModel() {
    private val args = savedStateHandle.toRoute<EditRoute>()
    private val eventId = args.eventId
    val createOnly = args.createOnly

    val title = MutableStateFlow("")

    val daysOfMonths = List(30) {
        NamedValue(
            "${it + 1}",
            it + 1
        )
    }
    val dayOfMonth = MutableStateFlow(daysOfMonths.first())

    val months = List(13) { NamedValue("${it + 1}", it) }
    val month = MutableStateFlow(months.first())

    init {
        viewModelScope.launch {
            eventRepo.get(eventId)?.also { event ->
                title.value = event.title
                dayOfMonth.value = daysOfMonths.first { it.value == event.dayOfMonth }
                month.value = months.first { it.value == event.month }
            }
        }
    }

    val canUpdateEvent = title.map { it.isNotBlank() }

    fun updateEvent() {
        viewModelScope.launch {
            val upsertEventUseCase = UpsertEventUseCase(eventRepo)
            upsertEventUseCase.execute(
                Event(
                    id = eventId,
                    title = title.value,
                    dayOfMonth = dayOfMonth.value.value,
                    month = month.value.value
                )
            )
        }
    }

    private val _canDeleteEvent = MutableStateFlow(true)
    val canDeleteEvent: StateFlow<Boolean> = _canDeleteEvent

    fun deleteEvent() {
        viewModelScope.launch {
            val deleteEventUseCase = DeleteEventUseCase(eventRepo)
            deleteEventUseCase.execute(
                Event(
                    id = this@EditViewModel.eventId,
                    title = title.value,
                    dayOfMonth = dayOfMonth.value.value,
                    month = month.value.value
                )
            )
        }
    }
}