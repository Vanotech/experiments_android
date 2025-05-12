package com.vanotech.experiments.ui.events

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph
import com.vanotech.experiments.feature.tvguide.TvGuideNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val items = listOf(
        destinationOf(
            LunarDatesNavGraph.icon(),
            LunarDatesNavGraph.label(),
            LunarDatesNavGraph.startDestination()
        ),
        destinationOf(
            TvGuideNavGraph.icon(),
            TvGuideNavGraph.label(),
            TvGuideNavGraph.startDestination()
        )
    )

    data class Destination(
        val icon: ImageVector,
        val label: String,
        val route: Any
    )

    private fun destinationOf(
        icon: ImageVector,
        @StringRes label: Int,
        route: Any
    ) = Destination(
        icon = icon,
        label = context.getString(label),
        route = route
    )
}