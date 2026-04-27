package com.vanotech.experiments.data.tvguide.internal

import android.content.Context
import com.vanotech.experiments.core.utils.datastore.SimpleDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TvGuideDataStore @Inject constructor(
    @ApplicationContext context: Context
) : SimpleDataStore(context, DATA_STORE_NAME) {

    private val showEpisodesPreference = booleanPreference(Keys.SHOW_EPISODES, true)
    val showEpisodesFlow = showEpisodesPreference.flow
    suspend fun setShowEpisodes(value: Boolean) = showEpisodesPreference.set(value)

    private val showMoviesPreference = booleanPreference(Keys.SHOW_MOVIES, true)
    val showMoviesFlow = showMoviesPreference.flow
    suspend fun setShowMovies(value: Boolean) = showMoviesPreference.set(value)

    private val startTimePreference = intPreference(
        Keys.START_TIME,
        LocalTime(19, 0).toSecondOfDay()
    )
    val startTimeFlow = startTimePreference.flow.map {
        LocalTime.fromSecondOfDay(it)
    }

    suspend fun setStartTime(value: LocalTime) = startTimePreference.set(value.toSecondOfDay())

    private val endTimePreference = intPreference(
        Keys.END_TIME,
        LocalTime(23, 59).toSecondOfDay()
    )
    val endTimeFlow = endTimePreference.flow.map {
        LocalTime.fromSecondOfDay(it)
    }

    suspend fun setEndTime(value: LocalTime) = endTimePreference.set(value.toSecondOfDay())

    companion object {
        private const val DATA_STORE_NAME = "tv_guide"

        private object Keys {
            const val SHOW_EPISODES = "show_episodes"
            const val SHOW_MOVIES = "show_movies"
            const val START_TIME = "start_time"
            const val END_TIME = "end_time"
        }
    }
}