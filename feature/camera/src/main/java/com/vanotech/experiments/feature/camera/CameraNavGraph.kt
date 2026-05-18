package com.vanotech.experiments.feature.camera

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.vanotech.experiments.core.ui.navigation.NavGraph
import com.vanotech.experiments.core.ui.navigation.Navigator
import com.vanotech.experiments.feature.camera.screens.edit.EditRoute
import com.vanotech.experiments.feature.camera.screens.edit.EditScreen
import com.vanotech.experiments.feature.camera.screens.home.HomeRoute
import com.vanotech.experiments.feature.camera.screens.home.HomeScreen

object CameraNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.CameraAlt

    override fun label(context: Context): String = context.getString(R.string.route_camera_home)

    override fun startRoute(): NavKey = HomeRoute

    override fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator) {
        scope.apply {
            entry<HomeRoute> {
                HomeScreen(
                    onEditRequest = { navigator.navigate(EditRoute) }
                )
            }
            entry<EditRoute> {
                EditScreen(
                    onDismissRequest = { navigator.goBack() }
                )
            }
        }
    }
}