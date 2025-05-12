package com.vanotech.experiments.feature.lunardates.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vanotech.experiments.core.ui.NamedValue
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val eventRepo: EventRepo
) : ViewModel() {
    private val args = savedStateHandle.toRoute<EventEditRoute>()
    private val eventId = args.eventId

    val title = MutableStateFlow("")

    val daysOfMonths = List(30) { NamedValue("${it + 1}", it + 1) }
    val dayOfMonth = MutableStateFlow(daysOfMonths.first())

    val months = List(13) { NamedValue("${it + 1}", it) }
    val month = MutableStateFlow(months.first())

    init {
        viewModelScope.launch {
            eventRepo.get(eventId)?.also {event->
                title.value = event.title
                dayOfMonth.value = daysOfMonths.first { it.value == event.dayOfMonth  }
                month.value = months.first { it.value == event.month  }
            }
        }
    }

    val canUpdateEvent = title.map { it.isNotBlank() }

    fun updateEvent() {
        viewModelScope.launch {
            eventRepo.upsert(
                Event(
                    id = eventId,
                    title = title.value,
                    dayOfMonth = dayOfMonth.value.value,
                    month = month.value.value
                )
            )
        }
    }

    val canDeleteEvent = MutableStateFlow(true)

    fun deleteEvent() {
        viewModelScope.launch {
            eventRepo.delete(
                Event(
                    id = eventId,
                    title = title.value,
                    dayOfMonth = dayOfMonth.value.value,
                    month = month.value.value
                )
            )
        }
    }
}