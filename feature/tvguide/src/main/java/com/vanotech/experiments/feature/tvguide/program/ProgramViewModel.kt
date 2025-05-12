package com.vanotech.experiments.feature.tvguide.program

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vanotech.experiments.data.tvguide.ListingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val listingRepo: ListingRepo
) : ViewModel() {
    private val args = savedStateHandle.toRoute<ProgramRoute>()
    val program = listingRepo.getAsFlow(args.paId)

    init {
        viewModelScope.launch {
            listingRepo.getProgram(args.paId)
        }
    }
}