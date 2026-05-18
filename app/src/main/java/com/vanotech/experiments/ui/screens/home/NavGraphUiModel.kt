package com.vanotech.experiments.ui.screens.home

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.vanotech.experiments.core.ui.navigation.NavGraph


@Immutable
internal data class NavGraphUiModel(
    val id: Int,
    val icon: ImageVector,
    val label: String,
    val route: NavKey
) {
    constructor(
        context: Context,
        id: Int,
        navGraph: NavGraph
    ) : this(
        id = id,
        icon = navGraph.icon(),
        label = navGraph.label(context),
        route = navGraph.startRoute()
    )
}
