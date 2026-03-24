package com.vanotech.experiments.feature.tvguide.screens.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.vanotech.experiments.feature.tvguide.screens.home.detail.DetailPane
import com.vanotech.experiments.feature.tvguide.screens.home.list.ListPane
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()
    val items = viewModel.listings.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isListVisible =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded
    val isDetailVisible =
        navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
    val isListAndDetailVisible = isListVisible && isDetailVisible

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                ListPane(
                    isListAndDetailVisible = isListAndDetailVisible,
                    items = items,
                    onItemClick = { listing ->
                        coroutineScope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, listing.id)
                        }
                    },
                    showEpisodes = uiState.showEpisodes,
                    onShowEpisodesChanged = {
                        coroutineScope.launch {
                            viewModel.setShowEpisodes(it)
                        }
                    },
                    showMovies = uiState.showMovies,
                    onShowMoviesChanged = {
                        coroutineScope.launch {
                            viewModel.setShowMovies(it)
                        }
                    },
                    startTime = uiState.startTime,
                    onStartTimeChanged = {
                        coroutineScope.launch {
                            viewModel.setStartTime(it)
                        }
                    },
                    endTime = uiState.endTime,
                    onEndTimeChanged = {
                        coroutineScope.launch {
                            viewModel.setEndTime(it)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                navigator.currentDestination?.contentKey?.also { listingId ->
                    viewModel.setListing(listingId)
                }
                DetailPane(
                    isListAndDetailVisible = isListAndDetailVisible,
                    listing = uiState.listing
                ) {
                    coroutineScope.launch {
                        navigator.navigateBack()
                    }
                }
            }
        }
    )
}
