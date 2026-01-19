package com.vanotech.experiments.ui.home

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.vanotech.experiments.core.ui.NavGraph


@Immutable
internal data class NavGraphUiModel(
    val id: Int,
    val icon: ImageVector,
    val label: String,
    private val route: Any
) {
    constructor(
        context: Context,
        id: Int,
        navGraph: NavGraph
    ) : this(
        id = id,
        icon = navGraph.icon(),
        label = context.getString(navGraph.label()),
        route = navGraph.startDestination()
    )

    fun navigate(navController: NavController) {
        navController.navigate(route)
    }
}
