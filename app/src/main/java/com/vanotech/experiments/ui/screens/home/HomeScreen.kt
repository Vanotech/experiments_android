package com.vanotech.experiments.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vanotech.experiments.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val gridCells = remember(adaptiveInfo) {
        HomeUiState.calculateGridCells(adaptiveInfo)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        items = uiState.navGraphs,
        onItemClick = { it.navigate(navController) },
        gridCells = gridCells,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    items: List<NavGraphUiModel>,
    onItemClick: (NavGraphUiModel) -> Unit,
    gridCells: GridCells,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_home))
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { contentPadding ->
        HomeContentGrid(
            items = items,
            onItemClick = onItemClick,
            gridCells = gridCells,
            modifier = Modifier.padding(contentPadding),
        )
    }
}

@Composable
private fun HomeContentGrid(
    items: List<NavGraphUiModel>,
    onItemClick: (NavGraphUiModel) -> Unit,
    gridCells: GridCells,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp)
) {
    LazyVerticalGrid(
        columns = gridCells,
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement
    ) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            NavGraphCard(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun NavGraphCard(
    item: NavGraphUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null
            )
            Text(
                text = item.label,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
private fun NavGraphCardPreview() {
    val item = NavGraphUiModel(
        id = 1,
        icon = Icons.Default.Home,
        label = "Lorem ipsum",
        route = Unit
    )
    NavGraphCard(item, onClick = {})
}
