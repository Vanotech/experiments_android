package com.vanotech.experiments.data.tvguide.local

import android.content.Context
import com.vanotech.experiments.core.utils.datastore.SimpleDataStore
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.Singleton

@Singleton
internal class SettingsDataStore(
    context: Context
) : SimpleDataStore(context, DATA_STORE_NAME) {

    private val showEpisodesPreference = booleanPreference(Keys.SHOW_EPISODES, true)
    val showEpisodesFlow = showEpisodesPreference.flow
    suspend fun setShowEpisodes(value: Boolean) = showEpisodesPreference.set(value)

    private val showMoviesPreference = booleanPreference(Keys.SHOW_MOVIES, true)
    val showMoviesFlow = showMoviesPreference.flow
    suspend fun setShowMovies(value: Boolean) = showMoviesPreference.set(value)

    private val startTimePreference = intPreference(
        Keys.START_TIME,
        fromLocalTime(defaultStartTime())
    )
    val startTimeFlow = startTimePreference.flow.map {
        toLocalTime(it)
    }

    suspend fun setStartTime(value: LocalTime) = startTimePreference.set(fromLocalTime(value))

    private val endTimePreference = intPreference(
        Keys.END_TIME,
        fromLocalTime(defaultEndTime())
    )
    val endTimeFlow = endTimePreference.flow.map {
        toLocalTime(it)
    }

    suspend fun setEndTime(value: LocalTime) = endTimePreference.set(fromLocalTime(value))

    companion object {
        private const val DATA_STORE_NAME = "tv_guide.settings"

        private object Keys {
            const val SHOW_EPISODES = "show_episodes"
            const val SHOW_MOVIES = "show_movies"
            const val START_TIME = "start_time"
            const val END_TIME = "end_time"
        }

        private fun defaultStartTime(): LocalTime = LocalTime(19, 0)

        private fun defaultEndTime(): LocalTime = LocalTime(23, 59)

        private fun fromLocalTime(value: LocalTime): Int = value.toSecondOfDay()

        private fun toLocalTime(value: Int): LocalTime = LocalTime.fromSecondOfDay(value)
    }
}