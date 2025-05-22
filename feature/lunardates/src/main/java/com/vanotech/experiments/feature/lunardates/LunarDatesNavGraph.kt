package com.vanotech.experiments.feature.lunardates

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.feature.lunardates.add.AddRoute
import com.vanotech.experiments.feature.lunardates.add.AddScreen
import com.vanotech.experiments.feature.lunardates.edit.EditRoute
import com.vanotech.experiments.feature.lunardates.edit.EditScreen
import com.vanotech.experiments.feature.lunardates.home.HomeRoute
import com.vanotech.experiments.feature.lunardates.home.HomeScreen

object LunarDatesNavGraph {

    fun icon(): ImageVector = Icons.Default.Event

    @StringRes
    fun label(): Int = R.string.route_lunar_dates_home

    fun startDestination(): Any = HomeRoute

    fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<HomeRoute> { HomeScreen(navController) }
            composable<AddRoute> { AddScreen(navController) }
            composable<EditRoute> { EditScreen(navController) }
        }
    }
}