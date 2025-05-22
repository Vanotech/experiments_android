package com.vanotech.experiments.data.lunardates.internal.db

import com.vanotech.experiments.data.lunardates.Event
import javax.inject.Inject


internal class EventDaoService @Inject constructor(
    private val dao: EventDao
) {
    suspend fun delete(item: Event) = dao.delete(item)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun get(id: Int) = dao.get(id)

    fun getAllAsPagingSource() = dao.getAllAsPagingSource()

    suspend fun upsert(item: Event) = dao.upsert(item)

    suspend fun upsert(items: List<Event>) = dao.upsert(items)
}