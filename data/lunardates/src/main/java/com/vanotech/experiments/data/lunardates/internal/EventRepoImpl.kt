package com.vanotech.experiments.data.lunardates.internal

import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo
import com.vanotech.experiments.data.lunardates.internal.db.EventDaoService
import javax.inject.Inject

internal class EventRepoImpl @Inject constructor(
    private val eventDaoService: EventDaoService,
) : EventRepo {
    override suspend fun delete(item: Event) = eventDaoService.delete(item)

    override suspend fun get(id: Int) = eventDaoService.get(id)

    override fun getAllAsPagingSource() = eventDaoService.getAllAsPagingSource()

    override suspend fun upsert(item: Event) = eventDaoService.upsert(item)

    override suspend fun upsert(items: Collection<Event>) = eventDaoService.upsert(items)
}