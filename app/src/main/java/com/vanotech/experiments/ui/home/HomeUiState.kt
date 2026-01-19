package com.vanotech.experiments.ui.home

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

internal data class HomeUiState(
    val navGraphs: List<NavGraphUiModel> = emptyList()
) {
    companion object {
        fun calculateGridCells(adaptiveInfo: WindowAdaptiveInfo): GridCells {
            val windowSizeClass = adaptiveInfo.windowSizeClass
            return when {
                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
                    -> GridCells.Adaptive(240.dp)

                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
                    -> GridCells.Fixed(2)

                else -> GridCells.Fixed(1)
            }
        }
    }
}