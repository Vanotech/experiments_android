package com.vanotech.experiments.feature.lunardates.edit

import com.vanotech.experiments.core.utils.NamedValue

internal data class EditUiState(
    val createOnly: Boolean,
    val title: String,
    val daysOfMonth: List<NamedValue<Int>>,
    val dayOfMonth: NamedValue<Int>,
    val months: List<NamedValue<Int>>,
    val month: NamedValue<Int>,
) {
    val canUpdateEvent = title.isNotBlank()
    val canDeleteEvent = true
}