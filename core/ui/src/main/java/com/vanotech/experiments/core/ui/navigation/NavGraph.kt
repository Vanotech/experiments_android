package com.vanotech.experiments.core.ui.navigation

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

interface NavGraph {

    fun icon(): ImageVector

    fun label(context: Context): String

    fun startRoute(): NavKey

    fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator)
}