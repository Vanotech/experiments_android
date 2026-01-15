package com.vanotech.experiments.feature.lunardates.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.window.core.layout.WindowSizeClass
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph
import com.vanotech.experiments.feature.lunardates.R
import com.vanotech.experiments.feature.lunardates.edit.EditRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_lunar_dates_home))
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    LunarDatesNavGraph.navigateToEdit(navController, 0)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.action_add_date)
                )
            }
        }
    ) { paddingValues ->
        val uiState = viewModel.uiState.collectAsState().value
        val items = uiState.events.collectAsLazyPagingItems()
        if (items.itemCount > 0) {
            Feed(
                items = items,
                navController = navController,
                paddingValues = paddingValues
            )
        } else {
            EmptyFeed(
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun Feed(
    items: LazyPagingItems<EventUiModel>,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val gridCells = remember(windowSizeClass) {
        when {
            windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) ->
                GridCells.Adaptive(240.dp)

            windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ->
                GridCells.Fixed(2)

            else -> GridCells.Fixed(1)
        }
    }

    LazyVerticalGrid(
        columns = gridCells,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id }
        ) { index ->
            val item = items[index]
            if (item != null) {
                Item(
                    item = item,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun EmptyFeed(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.hint_home_empty_feed),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Item(
    item: EventUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = item.lunarDate,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = item.gregorianDate(LocalContext.current),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun Item(
    item: EventUiModel,
    navController: NavController
) {
    Item(item) {
        item.navigate(navController)
    }
}

@Preview
@Composable
fun EventUiModelPreview() {
    val event = Event.mockData(0)
    val item = EventUiModel(event = event)
    Item(item) { }
}