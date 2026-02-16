package com.vanotech.experiments.data.lunardates.internal.db

import com.vanotech.experiments.data.lunardates.Event
import javax.inject.Inject


internal class EventDaoService @Inject constructor(
    private val dao: EventDao
) {
    suspend fun delete(item: Event) = dao.delete(item.toEventEntity())

    suspend fun get(id: Int): Event? = dao.get(id)?.toEvent()

    fun getAllAsPagingSource() = dao.getAllAsPagingSource()

    suspend fun upsert(item: Event) = dao.upsert(item.toEventEntity())

    suspend fun upsert(items: Collection<Event>) = dao.upsert(
        items.map {
            it.toEventEntity()
        }
    )
}