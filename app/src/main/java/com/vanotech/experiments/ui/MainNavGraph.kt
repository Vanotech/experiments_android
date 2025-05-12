package com.vanotech.experiments.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.R
import com.vanotech.experiments.ui.events.HomeRoute
import com.vanotech.experiments.ui.events.HomeScreen

object MainNavGraph {

    fun icon(): ImageVector = Icons.Default.Home

    @StringRes
    fun label(): Int = R.string.route_home

    fun startDestination(): Any = HomeRoute

    fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<HomeRoute> { HomeScreen(navController) }
        }
    }
}