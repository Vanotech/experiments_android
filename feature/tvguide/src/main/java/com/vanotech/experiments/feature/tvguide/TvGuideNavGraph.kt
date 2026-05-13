package com.vanotech.experiments.feature.tvguide

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.vanotech.experiments.core.ui.navigation.NavGraph
import com.vanotech.experiments.core.ui.navigation.Navigator
import com.vanotech.experiments.feature.tvguide.screens.home.HomeRoute
import com.vanotech.experiments.feature.tvguide.screens.home.HomeScreen

object TvGuideNavGraph : NavGraph {

    override fun icon(): ImageVector = Icons.Default.Tv

    override fun label(context: Context): String = context.getString(R.string.route_tv_guide_home)

    override fun startRoute(): NavKey = HomeRoute

    override fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator) {
        scope.apply {
            entry<HomeRoute> {
                HomeScreen()
            }
        }
    }
}