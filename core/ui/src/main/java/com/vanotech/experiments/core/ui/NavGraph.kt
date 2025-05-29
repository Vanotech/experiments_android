package com.vanotech.experiments.core.ui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavGraph {

    fun icon(): ImageVector

    @StringRes
    fun label(): Int

    fun startDestination(): Any

    fun register(navGraphBuilder: NavGraphBuilder, navController: NavController)
}