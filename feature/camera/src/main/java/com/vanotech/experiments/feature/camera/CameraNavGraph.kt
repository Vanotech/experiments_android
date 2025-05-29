package com.vanotech.experiments.feature.camera

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.core.ui.NavGraph
import com.vanotech.experiments.feature.camera.edit.EditRoute
import com.vanotech.experiments.feature.camera.edit.EditScreen
import com.vanotech.experiments.feature.camera.home.HomeRoute
import com.vanotech.experiments.feature.camera.home.HomeScreen

object CameraNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.CameraAlt

    override fun label(): Int = R.string.route_camera_home

    override fun startDestination(): Any = HomeRoute

    override fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<HomeRoute> { HomeScreen(navController) }
            composable<EditRoute> { EditScreen(navController) }
        }
    }
}