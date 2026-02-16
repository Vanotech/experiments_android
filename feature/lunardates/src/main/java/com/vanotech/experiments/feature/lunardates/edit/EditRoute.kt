package com.vanotech.experiments.feature.lunardates.edit

import kotlinx.serialization.Serializable

@Serializable
internal data class EditRoute(
    val eventId: Int
) {
    val createOnly = eventId == ADD_EVENT_ID

    companion object {
        const val ADD_EVENT_ID = 0
    }
}