package com.vanotech.experiments.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vanotech.experiments.feature.camera.CameraNavGraph
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph
import com.vanotech.experiments.feature.tvguide.TvGuideNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = MainNavGraph.startDestination()
            ) {
                val navGraphs = listOf(
                    CameraNavGraph,
                    MainNavGraph,
                    LunarDatesNavGraph,
                    TvGuideNavGraph
                )
                navGraphs.forEach{ navGraph->
                    navGraph.register(this, navController)
                }
            }
        }
    }
}