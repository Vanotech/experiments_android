package com.vanotech.experiments.feature.lunardates.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.window.core.layout.WindowWidthSizeClass
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
                    navController.navigate(route = EditRoute(0))
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.action_add_date)
                )
            }
        }
    ) { paddingValues ->
        val state = viewModel.state.collectAsState().value
        val items = state.events.collectAsLazyPagingItems()
        if (items.itemCount > 0) {
            Feed(
                items = items,
                navController = navController,
                paddingValues = paddingValues
            )
        } else {
            EmptyFeed(paddingValues = paddingValues)
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
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> GridCells.Fixed(1)
            WindowWidthSizeClass.MEDIUM -> GridCells.Fixed(2)
            else -> GridCells.Adaptive(240.dp)
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
            item?.Content(navController = navController)
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
            text = stringResource(R.string.hint_empty_dates),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}
