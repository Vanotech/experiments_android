package com.vanotech.experiments.data.tvguide.usecase

import com.vanotech.experiments.data.tvguide.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.Factory

@Factory
class GetStartTimeSettingUseCase(
    settingsRepository: SettingsRepository
) {
    val flow: Flow<LocalTime> = settingsRepository.startTime
}