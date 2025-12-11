package com.vanotech.experiments.data.media

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MediaRepo {
    suspend fun delete(item: Media)

    suspend fun get(id: Int): Media?

    fun getAllAsPagingData(): Flow<PagingData<Media>>

    suspend fun upsert(item: Media): Unit

    suspend fun upsert(items: Collection<Media>): Unit
}