package com.vanotech.experiments.data.tvguide.usecase

import com.vanotech.experiments.data.tvguide.repository.SettingsRepository
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.Factory

@Factory
class SetStartTimeSettingUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: LocalTime) {
        settingsRepository.setStartTime(value)
    }
}