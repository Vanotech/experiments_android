package com.vanotech.experiments.feature.tvguide.screens.home.settings.time

import androidx.compose.runtime.Composable
import com.vanotech.experiments.data.tvguide.ListingRepo
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components.TimeSettingViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class EndTimeSettingViewModel(
    listingRepo: ListingRepo
) : TimeSettingViewModel(
    listingRepo.endTime,
    listingRepo::setEndTime
) {
    companion object {
        @Composable
        fun viewModel(): EndTimeSettingViewModel {
            return koinViewModel()
        }
    }
}