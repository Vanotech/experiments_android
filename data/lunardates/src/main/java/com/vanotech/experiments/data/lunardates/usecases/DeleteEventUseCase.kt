package com.vanotech.experiments.data.lunardates.usecases

import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo

class DeleteEventUseCase(
    private val eventRepo: EventRepo
) {
    suspend fun execute(event: Event) {
        eventRepo.delete(event)
    }
}