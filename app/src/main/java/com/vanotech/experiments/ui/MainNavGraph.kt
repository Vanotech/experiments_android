package com.vanotech.experiments.ui

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.vanotech.experiments.R
import com.vanotech.experiments.core.ui.navigation.NavGraph
import com.vanotech.experiments.core.ui.navigation.Navigator
import com.vanotech.experiments.ui.screens.home.HomeRoute
import com.vanotech.experiments.ui.screens.home.HomeScreen

object MainNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Home

    override fun label(context: Context): String = context.getString(R.string.route_home)

    override fun startRoute(): NavKey = HomeRoute

    override fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator) {
        scope.apply {
            entry<HomeRoute> {
                HomeScreen(
                    onViewRequest = { navigator.navigate(it.route)}
                )
            }
        }
    }
}