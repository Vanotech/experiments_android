package com.vanotech.experiments.feature.lunardates

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.feature.lunardates.add.EventAddRoute
import com.vanotech.experiments.feature.lunardates.add.EventAddScreen
import com.vanotech.experiments.feature.lunardates.edit.EventEditRoute
import com.vanotech.experiments.feature.lunardates.edit.EventEditScreen
import com.vanotech.experiments.feature.lunardates.events.EventsRoute
import com.vanotech.experiments.feature.lunardates.events.EventsScreen

object LunarDatesNavGraph {

    fun icon(): ImageVector = Icons.Default.Event

    @StringRes
    fun label(): Int = R.string.route_event

    fun startDestination(): Any = EventsRoute

    fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<EventsRoute> { EventsScreen(navController) }
            composable<EventAddRoute> { EventAddScreen(navController) }
            composable<EventEditRoute> { EventEditScreen(navController) }
        }
    }
}