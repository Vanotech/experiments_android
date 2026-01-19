package com.vanotech.experiments.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.feature.camera.CameraNavGraph
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph
import com.vanotech.experiments.feature.media.MediaNavGraph
import com.vanotech.experiments.feature.tvguide.TvGuideNavGraph
import com.vanotech.experiments.ui.MainNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        val navGraphs = NAV_GRAPHS.mapIndexed { index, it ->
            NavGraphUiModel(context, index, it)
        }

        _uiState.update {
            it.copy(navGraphs = navGraphs)
        }
    }

    companion object {
        val NAV_GRAPHS = listOf(
            CameraNavGraph,
            LunarDatesNavGraph,
            MediaNavGraph,
            TvGuideNavGraph
        )

        val START_NAV_GRAPH = MainNavGraph
    }
}