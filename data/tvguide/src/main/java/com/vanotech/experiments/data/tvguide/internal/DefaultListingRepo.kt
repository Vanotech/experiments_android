package com.vanotech.experiments.data.tvguide.internal

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vanotech.experiments.data.tvguide.Listing
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.data.tvguide.internal.db.ListingDaoService
import com.vanotech.experiments.data.tvguide.internal.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.internal.db.toListing
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import com.vanotech.experiments.data.tvguide.internal.net.schema.Platform
import com.vanotech.experiments.data.tvguide.internal.net.schema.Region
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.Factory
import kotlin.time.Clock

@Factory
internal class DefaultListingRepo(
    private val listingDaoService: ListingDaoService,
    private val tvGuideApiService: TvGuideApiService,
    private val tvGuideDatabase: TvGuideDatabase,
    private val tvGuideDataStore: SettingsDataStore
) : ListingRepo {
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
    override fun getAllAsPagingData(config: PagingConfig): Flow<PagingData<Listing>> {
        return Pager(
            config = config,
            remoteMediator = GetListingsRemoteMediator(
                platform = Platform.VIRGIN,
                region = Region.NORTH_WEST,
                instant = Clock.System.now(),
                apiService = tvGuideApiService,
                database = tvGuideDatabase
            )
        ) {
            listingDaoService.getAllAsPagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                it.toListing()
            }
        }
    }

    override val showEpisodes = tvGuideDataStore.showEpisodesFlow
    override suspend fun setShowEpisodes(value: Boolean) = tvGuideDataStore.setShowEpisodes(value)

    override val showMovies = tvGuideDataStore.showMoviesFlow
    override suspend fun setShowMovies(value: Boolean) = tvGuideDataStore.setShowMovies(value)

    override val startTime = tvGuideDataStore.startTimeFlow
    override suspend fun setStartTime(value: LocalTime) = tvGuideDataStore.setStartTime(value)

    override val endTime = tvGuideDataStore.endTimeFlow
    override suspend fun setEndTime(value: LocalTime) = tvGuideDataStore.setEndTime(value)
}