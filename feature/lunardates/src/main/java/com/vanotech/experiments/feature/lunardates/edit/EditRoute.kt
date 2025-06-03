package com.vanotech.experiments.feature.lunardates.edit

import kotlinx.serialization.Serializable

@Serializable
internal data class EditRoute(
    val eventId: Int
) {
    val createOnly = eventId == 0
}