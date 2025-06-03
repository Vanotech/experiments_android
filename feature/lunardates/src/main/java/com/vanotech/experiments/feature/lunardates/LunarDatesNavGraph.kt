package com.vanotech.experiments.feature.lunardates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.feature.lunardates.edit.EditRoute
import com.vanotech.experiments.feature.lunardates.edit.EditScreen
import com.vanotech.experiments.feature.lunardates.home.HomeRoute
import com.vanotech.experiments.feature.lunardates.home.HomeScreen

object LunarDatesNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Event

    override fun label(): Int = R.string.route_lunar_dates_home

    override fun startDestination(): Any = HomeRoute

    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<HomeRoute> { HomeScreen(navController) }
            composable<EditRoute> { EditScreen(navController) }
        }
    }
}