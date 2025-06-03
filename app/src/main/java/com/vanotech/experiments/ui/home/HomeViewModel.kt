package com.vanotech.experiments.ui.home

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.feature.camera.CameraNavGraph
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph
import com.vanotech.experiments.feature.tvguide.TvGuideNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val navGraphs = listOf(
        CameraNavGraph,
        LunarDatesNavGraph,
        TvGuideNavGraph
    )

    val items = navGraphs.map { navGraph ->
        navGraph.toDestination()
    }

    data class Destination(
        val icon: ImageVector,
        val label: String,
        val route: Any
    )

    private fun NavGraph.toDestination() = Destination(
        icon = icon(),
        label = context.getString(label()),
        route = startDestination()
    )
}