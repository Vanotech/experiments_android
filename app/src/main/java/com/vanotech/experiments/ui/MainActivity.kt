package com.vanotech.experiments.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.vanotech.experiments.core.ui.navigation.Navigator
import com.vanotech.experiments.core.ui.navigation.rememberNavigationState
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
            startRoute = startRoute
        )

        val navigator = remember { Navigator(navigationState) }

        val entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator()
        )

        val entryProvider = entryProvider {
            val navGraphs = HomeViewModel.NAV_GRAPHS + startNavGraph
            navGraphs.forEach { navGraph ->
                navGraph.register(this, navigator)
            }
        }

        NavDisplay(
            backStack = navigator.backStack,
            onBack = { navigator.goBack() },
            entryDecorators = entryDecorators,
            entryProvider = entryProvider
        )
    }
}