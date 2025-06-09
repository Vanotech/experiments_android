package com.vanotech.experiments.data.tvguide

import androidx.paging.PagingSource
import com.vanotech.experiments.data.tvguide.internal.net.schema.Platform
import com.vanotech.experiments.data.tvguide.internal.net.schema.Region
import com.vanotech.experiments.data.tvguide.schema.Listing
import kotlinx.coroutines.flow.Flow
import java.time.LocalTime

interface ListingRepo {
    val showEpisodes: Flow<Boolean>
    val showMovies: Flow<Boolean>
    val startTime: Flow<LocalTime>
    val endTime: Flow<LocalTime>

    fun getAllAsPagingSource(): PagingSource<Int, Listing>

    fun getAsFlow(id: String): Flow<Listing?>

    suspend fun getListings(
        hours: Int,
        platform: String = Platform.VIRGIN,
        region: String = Region.NORTH_WEST,
    ): Result<Unit>

    suspend fun getProgram(paId: String): Result<Unit>

    suspend fun setShowEpisodes(value: Boolean)
    suspend fun setShowMovies(value: Boolean)
    suspend fun setStartTime(value: LocalTime)
    suspend fun setEndTime(value: LocalTime)
}