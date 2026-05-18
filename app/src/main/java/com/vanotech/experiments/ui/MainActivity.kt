package com.vanotech.experiments.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.vanotech.experiments.core.ui.navigation.Navigator
import com.vanotech.experiments.core.ui.navigation.rememberNavigationState
import com.vanotech.experiments.core.ui.navigation.toEntries
import com.vanotech.experiments.ui.screens.home.HomeViewModel
import com.vanotech.experiments.ui.theme.Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        val startNavGraph = HomeViewModel.START_NAV_GRAPH
        val startRoute = startNavGraph.startRoute()

        val navigationState = rememberNavigationState(
            startRoute = startRoute,
            topLevelRoutes = setOf(startRoute)
        )

        val navigator = remember { Navigator(navigationState) }

        val entryProvider = entryProvider {
            val navGraphs = HomeViewModel.NAV_GRAPHS + startNavGraph
            navGraphs.forEach { navGraph ->
                navGraph.register(this, navigator)
            }
        }

        NavDisplay(
            entries = navigationState.toEntries(entryProvider),
            onBack = { navigator.goBack() }
        )
    }
}