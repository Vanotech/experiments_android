package com.vanotech.experiments.data.tvguide

import com.vanotech.experiments.data.tvguide.internal.TvGuideDataStore
import com.vanotech.experiments.data.tvguide.internal.db.ListingDaoService
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import com.vanotech.experiments.data.tvguide.internal.net.schema.Platform
import com.vanotech.experiments.data.tvguide.internal.net.schema.Region
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class ListingRepo @Inject internal constructor(
    private val listingDaoService: ListingDaoService,
    private val tvGuideApiService: TvGuideApiService,
    private val tvGuideDataStore: TvGuideDataStore
) {
    val showEpisodes = tvGuideDataStore.showEpisodesFlow
    val showMovies = tvGuideDataStore.showMoviesFlow
    val startTime = tvGuideDataStore.startTimeFlow
    val endTime = tvGuideDataStore.endTimeFlow

    fun getAllAsPagingSource() = listingDaoService.getAllAsPagingSource()

    fun getAsFlow(id: String) = listingDaoService.getAsFlow(id)

    suspend fun getListings(
        hours: Int,
        platform: String = Platform.VIRGIN,
        region: String = Region.NORTH_WEST,
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

    suspend fun getProgram(paId: String): Result<Unit> {
        return try {
            val listing = tvGuideApiService.getProgram(paId)
            listingDaoService.upsert(listing)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun setShowEpisodes(value: Boolean) = tvGuideDataStore.setShowEpisodes(value)
    suspend fun setShowMovies(value: Boolean) = tvGuideDataStore.setShowMovies(value)
    suspend fun setStartTime(value: LocalTime) = tvGuideDataStore.setStartTime(value)
    suspend fun setEndTime(value: LocalTime) = tvGuideDataStore.setEndTime(value)
}