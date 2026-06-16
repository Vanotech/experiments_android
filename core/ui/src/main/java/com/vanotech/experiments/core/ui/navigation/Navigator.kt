package com.vanotech.experiments.core.ui.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(
    private val state: NavigationState
) {
    val backStack = state.backStack

    fun navigate(route: NavKey) {
        state.navigate(route)
    }

    fun goBack() {
        state.goBack()
    }
}