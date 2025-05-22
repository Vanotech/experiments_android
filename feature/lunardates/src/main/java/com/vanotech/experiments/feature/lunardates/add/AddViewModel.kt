package com.vanotech.experiments.feature.lunardates.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.core.ui.NamedValue
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AddViewModel @Inject constructor(
    private val eventRepo: EventRepo
) : ViewModel() {
    val title = MutableStateFlow("")

    val daysOfMonths = List(30) { NamedValue("${it + 1}", it + 1) }
    val dayOfMonth = MutableStateFlow(daysOfMonths.first())

    val months = List(13) { NamedValue("${it + 1}", it) }
    val month = MutableStateFlow(months.first())

    val canAddEvent = title.map { it.isNotBlank() }

    fun addEvent() {
        viewModelScope.launch {
            eventRepo.upsert(
                Event(
                    id = 0,
                    title = title.value,
                    dayOfMonth = dayOfMonth.value.value,
                    month = month.value.value
                )
            )
        }
    }
}