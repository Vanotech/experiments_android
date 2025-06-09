package com.vanotech.experiments.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vanotech.experiments.R
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.ui.home.HomeRoute
import com.vanotech.experiments.ui.home.HomeScreen

object MainNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Home

    override fun label(): Int = R.string.route_home

    override fun startDestination(): Any = MainRoute

    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<MainRoute>(startDestination = HomeRoute) {
            composable<HomeRoute> { HomeScreen(navController) }
        }
    }
}