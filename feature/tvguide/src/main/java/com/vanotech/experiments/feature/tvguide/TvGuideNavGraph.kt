package com.vanotech.experiments.feature.tvguide

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.feature.tvguide.detail.DetailRoute
import com.vanotech.experiments.feature.tvguide.detail.DetailScreen
import com.vanotech.experiments.feature.tvguide.home.HomeRoute
import com.vanotech.experiments.feature.tvguide.home.HomeScreen

object TvGuideNavGraph {

    fun icon(): ImageVector = Icons.Default.Tv

    @StringRes
    fun label(): Int = R.string.route_tv_guide_home

    fun startDestination(): Any = HomeRoute

    fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<HomeRoute> { HomeScreen(navController) }
            composable<DetailRoute> { DetailScreen(navController) }
        }
    }
}