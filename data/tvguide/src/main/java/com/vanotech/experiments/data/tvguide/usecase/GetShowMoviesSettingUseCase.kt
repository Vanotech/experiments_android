package com.vanotech.experiments.data.tvguide.usecase

import com.vanotech.experiments.data.tvguide.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetShowMoviesSettingUseCase(
    settingsRepository: SettingsRepository
) {
    val flow: Flow<Boolean> = settingsRepository.showMovies
}