package com.vanotech.experiments.data.tvguide

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalTime

interface ListingRepo {
    suspend fun fetch(id: String): Result<Unit>

    fun getAsFlow(id: String): Flow<Listing?>

    fun getAllAsPagingData(config: PagingConfig): Flow<PagingData<Listing>>

    val showEpisodes: Flow<Boolean>
    suspend fun setShowEpisodes(value: Boolean)

    val showMovies: Flow<Boolean>
    suspend fun setShowMovies(value: Boolean)

    val startTime: Flow<LocalTime>
    suspend fun setStartTime(value: LocalTime)

    val endTime: Flow<LocalTime>
    suspend fun setEndTime(value: LocalTime)
}