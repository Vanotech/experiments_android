package com.vanotech.experiments.data.lunardates

import androidx.paging.PagingSource
import com.vanotech.experiments.data.lunardates.internal.db.EventDaoService
import javax.inject.Inject

interface EventRepo {
    suspend fun delete(item: Event)

    suspend fun get(id: Int): Event?

    fun getAllAsPagingSource(): PagingSource<Int, Event>

    suspend fun upsert(item: Event): Unit

    suspend fun upsert(items: Collection<Event>): Unit
}