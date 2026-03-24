package com.vanotech.experiments.feature.camera

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.feature.camera.screens.edit.EditRoute
import com.vanotech.experiments.feature.camera.screens.edit.EditScreen
import com.vanotech.experiments.feature.camera.screens.home.HomeRoute
import com.vanotech.experiments.feature.camera.screens.home.HomeScreen

object CameraNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.CameraAlt

    override fun label(): Int = R.string.route_camera_home

    override fun startDestination(): Any = CameraRoute

    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation<CameraRoute>(startDestination = HomeRoute) {
            composable<HomeRoute> { HomeScreen(navController) }
            composable<EditRoute> { EditScreen(navController) }
        }
    }

    internal fun navigateToEdit(navController: NavController) {
        navController.navigate(EditRoute)
    }
}