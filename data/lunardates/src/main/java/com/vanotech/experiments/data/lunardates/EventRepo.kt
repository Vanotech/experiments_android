package com.vanotech.experiments.data.lunardates

import com.vanotech.experiments.data.lunardates.internal.db.EventDaoService
import javax.inject.Inject

class EventRepo @Inject constructor(
    private val eventDaoService: EventDaoService,
) {
    suspend fun delete(item: Event) = eventDaoService.delete(item)

    suspend fun get(id: Int) = eventDaoService.get(id)

    fun getAllAsPagingSource() = eventDaoService.getAllAsPagingSource()

    suspend fun upsert(item: Event) = eventDaoService.upsert(item)

    suspend fun upsert(items: List<Event>) = eventDaoService.upsert(items)
}