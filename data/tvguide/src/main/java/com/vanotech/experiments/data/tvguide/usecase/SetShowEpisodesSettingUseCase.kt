package com.vanotech.experiments.data.tvguide.usecase

import com.vanotech.experiments.data.tvguide.repository.SettingsRepository
import org.koin.core.annotation.Factory

@Factory
class SetShowEpisodesSettingUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) {
        settingsRepository.setShowEpisodes(value)
    }
}