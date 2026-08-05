package com.vanotech.experiments.data.tvguide.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalTime

interface SettingsRepository {
    val showEpisodes: Flow<Boolean>
    suspend fun setShowEpisodes(value: Boolean)

    val showMovies: Flow<Boolean>
    suspend fun setShowMovies(value: Boolean)

    val startTime: Flow<LocalTime>
    suspend fun setStartTime(value: LocalTime)

    val endTime: Flow<LocalTime>
    suspend fun setEndTime(value: LocalTime)
}