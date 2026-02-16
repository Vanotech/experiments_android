package com.vanotech.experiments.data.tvguide

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime

interface ListingRepo {
    val showEpisodes: Flow<Boolean>
    val showMovies: Flow<Boolean>
    val startTime: Flow<LocalTime>
    val endTime: Flow<LocalTime>

    suspend fun get(id: String): Result<Listing>

    fun getAsFlow(id: String): Flow<Listing?>

    fun getAllAsPagingData(config: PagingConfig): Flow<PagingData<Listing>>

    suspend fun setShowEpisodes(value: Boolean)
    suspend fun setShowMovies(value: Boolean)
    suspend fun setStartTime(value: LocalTime)
    suspend fun setEndTime(value: LocalTime)
}