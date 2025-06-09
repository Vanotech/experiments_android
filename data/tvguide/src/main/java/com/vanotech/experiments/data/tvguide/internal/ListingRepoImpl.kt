package com.vanotech.experiments.data.tvguide.internal

import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.data.tvguide.internal.db.ListingDaoService
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

internal class ListingRepoImpl @Inject constructor(
    private val listingDaoService: ListingDaoService,
    private val tvGuideApiService: TvGuideApiService,
    private val tvGuideDataStore: TvGuideDataStore
) : ListingRepo {
    override val showEpisodes = tvGuideDataStore.showEpisodesFlow
    override val showMovies = tvGuideDataStore.showMoviesFlow
    override val startTime = tvGuideDataStore.startTimeFlow
    override val endTime = tvGuideDataStore.endTimeFlow

    override fun getAllAsPagingSource() = listingDaoService.getAllAsPagingSource()

    override fun getAsFlow(id: String) = listingDaoService.getAsFlow(id)

    override suspend fun getListings(
        hours: Int,
        platform: String,
        region: String,
    ): Result<Unit> {
        return try {
            listingDaoService.deleteAll()
            val listings = coroutineScope {
                val startDateTime = LocalDateTime.now()
                val range = 0..hours step 3
                range.map { hour ->
                    async {
                        val dateTime = startDateTime.plusHours(hour.toLong())
                        tvGuideApiService.getListings(
                            platform = platform,
                            region = region,
                            date = dateTime.toLocalDate(),
                            hour = dateTime.hour
                        )
                    }
                }
            }.awaitAll().flatten()
            listingDaoService.upsert(listings)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getProgram(paId: String): Result<Unit> {
        return try {
            val listing = tvGuideApiService.getProgram(paId)
            listingDaoService.upsert(listing)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun setShowEpisodes(value: Boolean) = tvGuideDataStore.setShowEpisodes(value)
    override suspend fun setShowMovies(value: Boolean) = tvGuideDataStore.setShowMovies(value)
    override suspend fun setStartTime(value: LocalTime) = tvGuideDataStore.setStartTime(value)
    override suspend fun setEndTime(value: LocalTime) = tvGuideDataStore.setEndTime(value)
}