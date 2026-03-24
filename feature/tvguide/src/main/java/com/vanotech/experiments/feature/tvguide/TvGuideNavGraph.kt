package com.vanotech.experiments.feature.tvguide

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.feature.tvguide.screens.home.HomeRoute
import com.vanotech.experiments.feature.tvguide.screens.home.HomeScreen

object TvGuideNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Tv

    @StringRes
    override fun label(): Int = R.string.route_tv_guide_home

    override fun startDestination(): Any = TvGuideRoute

    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<TvGuideRoute>(startDestination = HomeRoute) {
            composable<HomeRoute> { HomeScreen(navController) }
        }
    }
}