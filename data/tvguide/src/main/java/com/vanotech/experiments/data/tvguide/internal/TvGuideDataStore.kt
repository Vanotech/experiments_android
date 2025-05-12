package com.vanotech.experiments.data.tvguide.internal

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject

class TvGuideDataStore @Inject constructor(
    @ApplicationContext context: Context
) : com.vanotech.experiments.core.utils.datastore.SimpleDataStore(context, DATA_STORE_NAME) {

    private val showEpisodesPreference = booleanPreference(Keys.SHOW_EPISODES, true)
    val showEpisodesFlow = showEpisodesPreference.flow
    suspend fun setShowEpisodes(value: Boolean) = showEpisodesPreference.set(value)

    private val showMoviesPreference = booleanPreference(Keys.SHOW_MOVIES, true)
    val showMoviesFlow = showMoviesPreference.flow
    suspend fun setShowMovies(value: Boolean) = showMoviesPreference.set(value)

    private val startTimePreference = longPreference(
        Keys.START_TIME,
        LocalTime.of(19, 0).toSecondOfDay().toLong()
    )
    val startTimeFlow = startTimePreference.flow.map {
        LocalTime.ofSecondOfDay(it)
    }

    suspend fun setStartTime(value: LocalTime) =
        startTimePreference.set(value.toSecondOfDay().toLong())

    private val endTimePreference = longPreference(
        Keys.END_TIME,
        LocalTime.of(23, 59).toSecondOfDay().toLong()
    )
    val endTimeFlow = endTimePreference.flow.map {
        LocalTime.ofSecondOfDay(it)
    }

    suspend fun setEndTime(value: LocalTime) =
        endTimePreference.set(value.toSecondOfDay().toLong())


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