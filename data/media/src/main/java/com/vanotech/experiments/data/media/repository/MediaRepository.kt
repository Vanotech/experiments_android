package com.vanotech.experiments.data.media.repository

import androidx.paging.PagingData
import com.vanotech.experiments.data.media.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun delete(item: Media)

    suspend fun get(id: Int): Media?

    fun getAllAsPagingData(): Flow<PagingData<Media>>

    suspend fun upsert(item: Media): Unit

    suspend fun upsert(items: Collection<Media>): Unit
}