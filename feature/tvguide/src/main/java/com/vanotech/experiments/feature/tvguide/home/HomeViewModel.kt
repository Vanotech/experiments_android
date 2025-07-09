package com.vanotech.experiments.feature.tvguide.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.vanotech.experiments.core.utils.TimeUtils
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val listingRepo: ListingRepo
) : ViewModel() {
    val showEpisodes = listingRepo.showEpisodes
    val showMovies = listingRepo.showMovies
    val startTime = listingRepo.startTime
    val endTime = listingRepo.endTime

    private val pager = Pager(
        config = PagingConfig(pageSize = 50)
    ) {
        listingRepo.getAllAsPagingSource()
    }

    private val pagingDataFlow = pager.flow.cachedIn(viewModelScope)

    val items = combine(
        pagingDataFlow,
        showEpisodes,
        showMovies,
        startTime,
        endTime
    ) { pagingData, showEpisodes, showMovies, startTime, endTime ->
        val now = System.currentTimeMillis()
        pagingData.filter { listing ->
            val endAt = listing.startAt.plus(listing.duration).toEpochMilli()
            when {
                endAt < now -> false
                !isPrimeTime(listing, startTime, endTime) -> false
                isShowType(listing, ListingType.EPISODE) -> showEpisodes
                isShowType(listing, ListingType.MOVIE) -> showMovies
                else -> true
            }
        }
    }.map { pagingData ->
        pagingData.map { listing ->
            HomeItem(listing)
        }
    }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            listingRepo.getListings(12)
        }
    }

    fun getListing(id: String): Flow<Listing?> {
        viewModelScope.launch {
            listingRepo.getProgram(id)
        }
        return listingRepo.getAsFlow(id)
    }

    fun setShowEpisodes(value: Boolean) {
        viewModelScope.launch {
            listingRepo.setShowEpisodes(value)
        }
    }

    fun setShowMovies(value: Boolean) {
        viewModelScope.launch {
            listingRepo.setShowMovies(value)
        }
    }

    fun setStartTime(value: LocalTime) {
        viewModelScope.launch {
            listingRepo.setStartTime(value)
        }
    }

    fun setEndTime(value: LocalTime) {
        viewModelScope.launch {
            listingRepo.setEndTime(value)
        }
    }

    companion object {
        private fun isShowType(
            listing: Listing,
            showType: ListingType
        ): Boolean = listing.type == showType

        private fun isPrimeTime(
            listing: Listing,
            startTime: LocalTime,
            endTime: LocalTime
        ): Boolean {
            val listingStartTime = TimeUtils.toLocalTime(listing.startAt)
            return (startTime <= listingStartTime) && (listingStartTime <= endTime)
        }
    }
}