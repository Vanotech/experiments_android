package com.vanotech.experiments.feature.lunardates.home

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


@Composable
internal fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val items = viewModel.events.collectAsLazyPagingItems()

    HomeScreen(
        items = items,
        onItemClick = { it.navigate(navController) },
        onNavigateToEdit = { LunarDatesNavGraph.navigateToEdit(navController, 0) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    items: LazyPagingItems<EventUiModel>,
    onItemClick: (EventUiModel) -> Unit,
    onNavigateToEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_lunar_dates_home))
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToEdit) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.action_add_date)
                )
            }
        }
    ) { innerPadding ->
        if (items.itemCount > 0) {
            HomeContentGrid(
                items = items,
                onItemClick = onItemClick,
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            EmptyFeed(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun HomeContentGrid(
    items: LazyPagingItems<EventUiModel>,
    onItemClick: (EventUiModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp)
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
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id }
        ) { index ->
            val item = items[index]
            if (item != null) {
                EventCard(
                    item = item,
                    onClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun EmptyFeed(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
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
private fun EventCard(
    item: EventUiModel,
    onClick: (EventUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(item) },
        modifier = modifier.fillMaxWidth(),
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

@Preview
@Composable
fun EventCardPreview() {
    val event = Event.mockData(0)
    val item = EventUiModel(event = event)
    EventCard(
        item = item,
        onClick = { }
    )
}