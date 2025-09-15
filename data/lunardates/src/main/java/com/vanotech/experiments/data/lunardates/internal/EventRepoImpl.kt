package com.vanotech.experiments.data.lunardates.internal

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo
import com.vanotech.experiments.data.lunardates.internal.db.EventDaoService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class EventRepoImpl @Inject constructor(
    private val eventDaoService: EventDaoService,
) : EventRepo {
    override suspend fun delete(item: Event) = eventDaoService.delete(item)

    override suspend fun get(id: Int) = eventDaoService.get(id)

    override fun getAllAsPagingData(): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(pageSize = 50)
        ) {
            eventDaoService.getAllAsPagingSource()
        }.flow
    }

    override suspend fun upsert(item: Event) = eventDaoService.upsert(item)

    override suspend fun upsert(items: Collection<Event>) = eventDaoService.upsert(items)
}