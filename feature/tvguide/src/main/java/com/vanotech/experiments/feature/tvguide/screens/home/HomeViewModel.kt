package com.vanotech.experiments.feature.tvguide.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.vanotech.experiments.core.utils.DateTimeUtils
import com.vanotech.experiments.data.tvguide.Listing
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.data.tvguide.ListingType
import com.vanotech.experiments.feature.tvguide.screens.home.list.ListingUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.KoinViewModel
import kotlin.time.Clock

@KoinViewModel
internal class HomeViewModel(
    private val listingRepo: ListingRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            listingRepo.showEpisodes.collectLatest { showEpisodes ->
                _uiState.update { it.copy(showEpisodes = showEpisodes) }
            }
        }
        viewModelScope.launch {
            listingRepo.showMovies.collectLatest { showMovies ->
                _uiState.update { it.copy(showMovies = showMovies) }
            }
        }
        viewModelScope.launch {
            listingRepo.startTime.collectLatest { startTime ->
                _uiState.update { it.copy(startTime = startTime) }
            }
        }
        viewModelScope.launch {
            listingRepo.endTime.collectLatest { endTime ->
                _uiState.update { it.copy(endTime = endTime) }
            }
        }
    }

    val listings: Flow<PagingData<ListingUiModel>> = run {
        val pagingData = listingRepo.getAllAsPagingData(
            config = PagingConfig(pageSize = 50)
        ).cachedIn(viewModelScope)

        combine(
            pagingData,
            uiState,
        ) { pagingData, uiState ->
            pagingData to uiState
        }.map { (pagingData, uiState) ->
            val now = Clock.System.now()
            pagingData.filter { listing ->
                val endAt = listing.startAt + listing.duration
                when {
                    endAt < now -> false
                    !isValidTime(listing, uiState.startTime, uiState.endTime) -> false
                    isValidType(listing, ListingType.EPISODE) -> uiState.showEpisodes
                    isValidType(listing, ListingType.MOVIE) -> uiState.showMovies
                    else -> true
                }
            }
        }.map { pagingData ->
            pagingData.map { listing ->
                ListingUiModel(listing)
            }
        }.cachedIn(viewModelScope)
    }

    private var setListingJob: Job? = null

    fun setListing(id: String) {
        setListingJob?.cancel()
        setListingJob = viewModelScope.launch {
            listingRepo.get(id)
            val listing = listingRepo.getAsFlow(id)
            listing.collectLatest { listing ->
                _uiState.update { it.copy(listing = listing) }
            }
        }
    }

    suspend fun setShowEpisodes(value: Boolean) {
        listingRepo.setShowEpisodes(value)
    }

    suspend fun setShowMovies(value: Boolean) {
        listingRepo.setShowMovies(value)
    }

    suspend fun setStartTime(value: LocalTime) {
        listingRepo.setStartTime(value)
    }

    suspend fun setEndTime(value: LocalTime) {
        listingRepo.setEndTime(value)
    }

    companion object {
        private fun isValidType(
            listing: Listing,
            showType: ListingType
        ): Boolean = listing.type == showType

        private fun isValidTime(
            listing: Listing,
            startTime: LocalTime,
            endTime: LocalTime
        ): Boolean {
            val listingStartTime = DateTimeUtils.toLocalDateTime(listing.startAt).time
            return (startTime <= listingStartTime) && (listingStartTime <= endTime)
        }
    }
}