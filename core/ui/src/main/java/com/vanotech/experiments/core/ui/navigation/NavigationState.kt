package com.vanotech.experiments.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Composable
fun rememberNavigationState(
    startRoute: NavKey
): NavigationState {
    val backStack = rememberNavBackStack(startRoute)
    return remember {
        NavigationState(
            backStack = backStack
        )
    }
}

class NavigationState(
    val backStack: NavBackStack<NavKey>
) {
    fun navigate(key: NavKey) {
        backStack.add(key)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
