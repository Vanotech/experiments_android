package com.vanotech.experiments.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.feature.camera.CameraNavGraph
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph
import com.vanotech.experiments.feature.tvguide.TvGuideNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val navGraphs = listOf(
        CameraNavGraph,
        LunarDatesNavGraph,
        TvGuideNavGraph
    )

    val items = navGraphs.map { navGraph ->
        HomeUiModel(
            icon = navGraph.icon(),
            label = context.getString(navGraph.label()),
            route = navGraph.startDestination()
        )
    }
}