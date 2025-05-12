package com.vanotech.experiments.data.tvguide

import android.content.Context
import com.vanotech.experiments.data.tvguide.internal.ListingsWorker
import com.vanotech.experiments.data.tvguide.internal.TvGuideDataStore
import com.vanotech.experiments.data.tvguide.internal.db.ListingDaoService
import com.vanotech.experiments.data.tvguide.internal.net.TvGuideApiService
import com.vanotech.experiments.data.tvguide.internal.net.schema.Platform
import com.vanotech.experiments.data.tvguide.internal.net.schema.Region
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class ListingRepo @Inject constructor(
    @ApplicationContext context: Context,
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

    init {
        ListingsWorker.enqueue(context)
    }

    suspend fun getListings(hours: Int): Result<Unit> {
        return try {
            listingDaoService.deleteAll()
            val startDateTime = LocalDateTime.now()
            val range = 0..hours
            range.forEach { hour ->
                val dateTime = startDateTime.plusHours(hour.toLong())
                val listings = tvGuideApiService.getListings(
                    platform = Platform.VIRGIN,
                    region = Region.NORTH_WEST,
                    date = dateTime.toLocalDate(),
                    hour = dateTime.hour
                )
                listingDaoService.upsert(listings)
            }
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