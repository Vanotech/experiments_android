package com.vanotech.experiments.data.lunardates.usecases

import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo

class UpsertEventUseCase(
    private val repo: EventRepo
) {
    suspend fun execute(event: Event) {
        repo.upsert(event)
    }
}