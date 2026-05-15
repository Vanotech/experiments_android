package com.vanotech.experiments.feature.tvguide.screens.home.settings.time

import androidx.compose.runtime.Composable
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components.TimeSettingViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class StartTimeSettingViewModel(
    listingRepo: ListingRepo
) : TimeSettingViewModel(
    listingRepo.startTime,
    listingRepo::setStartTime
) {
    companion object {
        @Composable
        fun viewModel(): StartTimeSettingViewModel {
            return koinViewModel()
        }
    }
}