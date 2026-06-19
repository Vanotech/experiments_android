package com.vanotech.experiments.feature.tvguide.screens.home.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanotech.experiments.data.tvguide.ListingRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel
import org.koin.core.parameter.parametersOf

@KoinViewModel
internal class DetailViewModel(
    @InjectedParam args: DetailRoute,
    private val listingRepo: ListingRepo
) : ViewModel() {
    private val listingId = args.listingId

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        viewModelScope.launch {
            listingRepo.fetch(listingId)
        }
        viewModelScope.launch {
            val listing = listingRepo.getAsFlow(listingId)
            listing.collectLatest { listing ->
                _uiState.update {
                    it.copy(listing = listing)
                }
            }
        }
    }

    companion object {
        @Composable
        fun viewModel(args: DetailRoute): DetailViewModel {
            return koinViewModel(
                parameters = { parametersOf(args) }
            )
        }
    }
}