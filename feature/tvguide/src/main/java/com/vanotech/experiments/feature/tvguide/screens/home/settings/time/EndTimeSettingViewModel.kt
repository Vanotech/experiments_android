package com.vanotech.experiments.feature.tvguide.screens.home.settings.time

import androidx.compose.runtime.Composable
import com.vanotech.experiments.data.tvguide.repository.ListingRepository
import com.vanotech.experiments.data.tvguide.usecase.GetEndTimeSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.SetEndTimeSettingUseCase
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components.TimeSettingViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class EndTimeSettingViewModel(
    getEndTimeSettingUseCase: GetEndTimeSettingUseCase,
    setEndTimeSettingUseCase: SetEndTimeSettingUseCase
) : TimeSettingViewModel(
    getEndTimeSettingUseCase.flow,
    setEndTimeSettingUseCase::invoke
) {
    companion object {
        @Composable
        fun viewModel(): EndTimeSettingViewModel {
            return koinViewModel()
        }
    }
}