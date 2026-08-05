package com.vanotech.experiments.data.tvguide.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vanotech.experiments.data.tvguide.local.db.ListingDaoService
import com.vanotech.experiments.data.tvguide.local.db.ScheduleDaoService
import com.vanotech.experiments.data.tvguide.local.db.TvGuideDatabase
import com.vanotech.experiments.data.tvguide.local.db.model.ListingView
import com.vanotech.experiments.data.tvguide.model.Listing
import com.vanotech.experiments.data.tvguide.remote.api.TvGuideApiService
import com.vanotech.experiments.data.tvguide.remote.api.model.Platform
import com.vanotech.experiments.data.tvguide.remote.api.model.Region
import com.vanotech.experiments.data.tvguide.remote.api.toSchedulePartial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import kotlin.time.Clock

@Factory
internal class DefaultListingRepository(
    private val listingDaoService: ListingDaoService,
    private val scheduleDaoService: ScheduleDaoService,
    private val tvGuideApiService: TvGuideApiService,
    private val tvGuideDatabase: TvGuideDatabase
) : ListingRepository {
    override suspend fun fetch(id: String) {
        val response = tvGuideApiService.fetchSingle(id)
        val newSchedule = response.toSchedulePartial()
        scheduleDaoService.upsert(newSchedule)
    }

    override fun getAsFlow(id: String) = listingDaoService.getAsFlow(id).map {
        it?.toListing()
    }

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

    companion object {
        private fun ListingView.toListing() = Listing(
            id = id,
            title = title,
            type = type,
            imageUrl = imageUrl,
            startAt = startAt,
            duration = duration,
            summary = summary,
            channelTitle = channelTitle,
            channelLogoUrl = channelLogoUrl
        )
    }
}