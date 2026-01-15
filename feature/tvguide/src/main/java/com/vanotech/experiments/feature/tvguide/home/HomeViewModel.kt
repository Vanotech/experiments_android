package com.vanotech.experiments.feature.tvguide.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.vanotech.experiments.core.utils.TimeUtils
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.data.tvguide.schema.ListingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        initListings()
    }

    private fun initListings() {
        val pagingDataFlow = listingRepo.getAllAsPagingData().cachedIn(viewModelScope)

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
                ListingUiModel(listing)
            }
        }.cachedIn(viewModelScope)

        _uiState.update {
            it.copy(listings = items)
        }
    }

    private var getListingJob: Job? = null
    fun getListing(id: String) {
        getListingJob?.cancel()
        getListingJob = viewModelScope.launch {
            listingRepo.get(id).getOrNull()
            val listing = listingRepo.getAsFlow(id)
            _uiState.update { it.copy(listing = listing) }
        }
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