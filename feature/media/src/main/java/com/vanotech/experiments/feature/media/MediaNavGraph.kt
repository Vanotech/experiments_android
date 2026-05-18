package com.vanotech.experiments.feature.media

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.vanotech.experiments.core.ui.navigation.NavGraph
import com.vanotech.experiments.core.ui.navigation.Navigator
import com.vanotech.experiments.feature.media.screens.home.HomeRoute
import com.vanotech.experiments.feature.media.screens.home.HomeScreen
import com.vanotech.experiments.feature.media.screens.view.ViewRoute
import com.vanotech.experiments.feature.media.screens.view.ViewScreen

object MediaNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Movie

    override fun label(context: Context): String = context.getString(R.string.route_media_home)

    override fun startRoute(): NavKey = HomeRoute

    override fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator) {
        scope.apply {
            entry<HomeRoute> {
                HomeScreen(
                    onViewRequest = { navigator.navigate(ViewRoute(it.id)) }
                )
            }
            entry<ViewRoute> { key ->
                ViewScreen(key)
            }
        }
    }
}