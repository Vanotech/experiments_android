package com.vanotech.experiments.feature.tvguide.screens.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.vanotech.experiments.core.ui.navigation.BottomSheetSceneStrategy
import com.vanotech.experiments.feature.tvguide.screens.home.detail.DetailRoute
import com.vanotech.experiments.feature.tvguide.screens.home.detail.DetailScreen
import com.vanotech.experiments.feature.tvguide.screens.home.list.ListRoute
import com.vanotech.experiments.feature.tvguide.screens.home.list.ListScreen
import com.vanotech.experiments.feature.tvguide.screens.home.settings.SettingsRoute
import com.vanotech.experiments.feature.tvguide.screens.home.settings.SettingsScreen
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.EndTimeSettingRoute
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.EndTimeSettingScreen
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.StartTimeSettingRoute
import com.vanotech.experiments.feature.tvguide.screens.home.settings.time.StartTimeSettingScreen

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class
)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(ListRoute)
    val entryDecorators = listOf(
        rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
        rememberViewModelStoreNavEntryDecorator()
    )
    val listDetailSceneStrategy = rememberListDetailSceneStrategy<NavKey>()
    val sceneStrategies = listOf(
        listDetailSceneStrategy,
        remember { BottomSheetSceneStrategy() },
        remember { DialogSceneStrategy() }
    )
    val isExpandedLayout = listDetailSceneStrategy.directive.maxHorizontalPartitions > 1
    Scaffold { contentPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = modifier
                .padding(contentPadding)
                .consumeWindowInsets(WindowInsets.statusBars),
            entryDecorators = entryDecorators,
            sceneStrategies = sceneStrategies,
            entryProvider = entryProvider {
                entry<ListRoute>(
                    metadata = ListDetailSceneStrategy.listPane()
                ) {
                    ListScreen(
                        onSettingsRequest = {
                            backStack.add(SettingsRoute)
                        },
                        onViewRequest = {
                            backStack.add(DetailRoute(it.id))
                        }
                    )
                }
                entry<DetailRoute>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) { key ->

                    DetailScreen(
                        args = key,
                        isExpandedLayout = isExpandedLayout,
                        onDismissRequest = { backStack.removeLastOrNull() },
                    )
                }
                entry<SettingsRoute>(
                    metadata = BottomSheetSceneStrategy.bottomSheet()
                ) {
                    SettingsScreen(
                        onEditStartTimeRequest = { backStack.add(StartTimeSettingRoute) },
                        onEditEndTimeRequest = { backStack.add(EndTimeSettingRoute) },
                    )
                }
                entry<StartTimeSettingRoute>(
                    metadata = DialogSceneStrategy.dialog(                    )
                ) {
                    StartTimeSettingScreen(
                        onDismissRequest = { backStack.removeLastOrNull() }
                    )
                }
                entry<EndTimeSettingRoute>(
                    metadata = DialogSceneStrategy.dialog()
                ) {
                    EndTimeSettingScreen(
                        onDismissRequest = { backStack.removeLastOrNull() }
                    )
                }
            }
        )
    }
}
