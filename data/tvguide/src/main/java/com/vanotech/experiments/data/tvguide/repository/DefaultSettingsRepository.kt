package com.vanotech.experiments.data.tvguide.repository

import com.vanotech.experiments.data.tvguide.local.SettingsDataStore
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.Factory

@Factory
internal class DefaultSettingsRepository(
    private val tvGuideDataStore: SettingsDataStore
) : SettingsRepository {
    override val showEpisodes = tvGuideDataStore.showEpisodesFlow
    override suspend fun setShowEpisodes(value: Boolean) =
        tvGuideDataStore.setShowEpisodes(value)

    override val showMovies = tvGuideDataStore.showMoviesFlow
    override suspend fun setShowMovies(value: Boolean) = tvGuideDataStore.setShowMovies(value)

    override val startTime = tvGuideDataStore.startTimeFlow
    override suspend fun setStartTime(value: LocalTime) = tvGuideDataStore.setStartTime(value)

    override val endTime = tvGuideDataStore.endTimeFlow
    override suspend fun setEndTime(value: LocalTime) = tvGuideDataStore.setEndTime(value)
}