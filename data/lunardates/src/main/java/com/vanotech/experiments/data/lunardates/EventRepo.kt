package com.vanotech.experiments.data.lunardates

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface EventRepo {
    suspend fun delete(item: Event)

    suspend fun get(id: Int): Event?

    fun getAllAsPagingData(config: PagingConfig): Flow<PagingData<Event>>

    suspend fun upsert(item: Event): Unit

    suspend fun upsert(items: Collection<Event>): Unit
}