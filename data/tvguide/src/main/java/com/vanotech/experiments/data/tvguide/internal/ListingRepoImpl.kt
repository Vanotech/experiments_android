package com.vanotech.experiments.data.tvguide.internal

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.data.tvguide.internal.db.ListingDaoService
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import com.vanotech.experiments.data.tvguide.internal.net.schema.Platform
import com.vanotech.experiments.data.tvguide.internal.net.schema.Region
import com.vanotech.experiments.data.tvguide.schema.Listing
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalTime
import javax.inject.Inject

internal class ListingRepoImpl @Inject constructor(
    private val listingDaoService: ListingDaoService,
    private val tvGuideApiService: TvGuideApiService,
    private val tvGuideDatabase: TvGuideDatabase,
    private val tvGuideDataStore: TvGuideDataStore
) : ListingRepo {
    override val showEpisodes = tvGuideDataStore.showEpisodesFlow
    override val showMovies = tvGuideDataStore.showMoviesFlow
    override val startTime = tvGuideDataStore.startTimeFlow
    override val endTime = tvGuideDataStore.endTimeFlow

    override suspend fun get(id: String): Result<Listing> {
        return try {
            val listing = tvGuideApiService.getSingle(id)
            listingDaoService.upsert(listing)
            Result.success(listing)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun getAsFlow(id: String) = listingDaoService.getAsFlow(id)

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllAsPagingData(): Flow<PagingData<Listing>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            remoteMediator = GetListingsRemoteMediator(
                platform = Platform.VIRGIN,
                region = Region.NORTH_WEST,
                instant = Instant.now(),
                apiService = tvGuideApiService,
                database = tvGuideDatabase
            )
        ) {
            listingDaoService.getAllAsPagingSource()
        }.flow
    }

    override suspend fun setShowEpisodes(value: Boolean) = tvGuideDataStore.setShowEpisodes(value)
    override suspend fun setShowMovies(value: Boolean) = tvGuideDataStore.setShowMovies(value)
    override suspend fun setStartTime(value: LocalTime) = tvGuideDataStore.setStartTime(value)
    override suspend fun setEndTime(value: LocalTime) = tvGuideDataStore.setEndTime(value)
}