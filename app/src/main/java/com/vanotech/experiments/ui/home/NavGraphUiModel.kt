package com.vanotech.experiments.ui.home

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.vanotech.experiments.core.ui.NavGraph


internal data class NavGraphUiModel(
    val icon: ImageVector,
    val label: String,
    private val route: Any
) {
    constructor(context: Context, navGraph: NavGraph) : this(
        icon = navGraph.icon(),
        label = context.getString(navGraph.label()),
        route = navGraph.startDestination()
    )

    fun navigate(navController: NavController) {
        navController.navigate(route)
    }
}
