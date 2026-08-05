package com.vanotech.experiments.data.tvguide.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanotech.experiments.data.tvguide.model.Listing
import kotlinx.coroutines.flow.Flow

interface ListingRepository {
    suspend fun fetch(id: String)

    fun getAsFlow(id: String): Flow<Listing?>

    fun getAllAsPagingData(config: PagingConfig): Flow<PagingData<Listing>>
}