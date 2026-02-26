package com.vanotech.experiments.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vanotech.experiments.ui.home.HomeViewModel
import com.vanotech.experiments.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            Theme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeViewModel.START_NAV_GRAPH.startDestination()
        ) {
            HomeViewModel.START_NAV_GRAPH.also { navGraph ->
                navGraph.register(this, navController)

            }
            HomeViewModel.NAV_GRAPHS.forEach { navGraph ->
                navGraph.register(this, navController)
            }
        }

    }
}