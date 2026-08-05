package com.vanotech.experiments.feature.tvguide.screens.home.settings.time

import androidx.compose.runtime.Composable
import com.vanotech.experiments.data.tvguide.usecase.GetStartTimeSettingUseCase
import com.vanotech.experiments.data.tvguide.usecase.SetStartTimeSettingUseCase
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.components.TimeSettingViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class StartTimeSettingViewModel(
    getStartTimeSettingUseCase: GetStartTimeSettingUseCase,
    setStartTimeSettingUseCase: SetStartTimeSettingUseCase
) : TimeSettingViewModel(
    getStartTimeSettingUseCase.flow,
    setStartTimeSettingUseCase::invoke
) {
    companion object {
        @Composable
        fun viewModel(): StartTimeSettingViewModel {
            return koinViewModel()
        }
    }
}