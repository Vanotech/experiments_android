package com.vanotech.experiments.feature.tvguide

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vanotech.experiments.feature.tvguide.listings.ListingsRoute
import com.vanotech.experiments.feature.tvguide.listings.ListingsScreen
import com.vanotech.experiments.feature.tvguide.program.ProgramRoute
import com.vanotech.experiments.feature.tvguide.program.ProgramScreen

object TvGuideNavGraph {

    fun icon(): ImageVector = Icons.Default.Tv

    @StringRes
    fun label(): Int = R.string.route_listing

    fun startDestination(): Any = ListingsRoute

    fun register(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            composable<ListingsRoute> { ListingsScreen(navController) }
            composable<ProgramRoute> { ProgramScreen(navController) }
        }
    }
}