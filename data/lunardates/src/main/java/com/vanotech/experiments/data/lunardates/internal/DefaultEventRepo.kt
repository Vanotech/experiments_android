package com.vanotech.experiments.data.lunardates.internal

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.data.lunardates.EventRepo
import com.vanotech.experiments.data.lunardates.internal.db.EventDaoService
import com.vanotech.experiments.data.lunardates.internal.db.toEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultEventRepo @Inject constructor(
    private val eventDaoService: EventDaoService,
) : EventRepo {
    override suspend fun delete(item: Event) = eventDaoService.delete(item)

    override suspend fun get(id: Int) = eventDaoService.get(id)

    override fun getAllAsPagingData(config: PagingConfig): Flow<PagingData<Event>> {
        return Pager(
            config = config
        ) {
            eventDaoService.getAllAsPagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                it.toEvent()
            }
        }
    }

    override suspend fun upsert(item: Event) = eventDaoService.upsert(item)

    override suspend fun upsert(items: Collection<Event>) = eventDaoService.upsert(items)
}