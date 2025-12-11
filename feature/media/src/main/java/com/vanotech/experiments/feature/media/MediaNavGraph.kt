package com.vanotech.experiments.feature.media

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.feature.media.home.HomeRoute
import com.vanotech.experiments.feature.media.home.HomeScreen
import com.vanotech.experiments.feature.media.view.ViewRoute
import com.vanotech.experiments.feature.media.view.ViewScreen

object MediaNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Movie

    @StringRes
    override fun label(): Int = R.string.route_media_home

    override fun startDestination(): Any = MediaRoute

    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<MediaRoute>(startDestination = HomeRoute) {
            composable<HomeRoute> { HomeScreen(navController) }
            composable<ViewRoute> { ViewScreen(navController) }
        }
    }
}