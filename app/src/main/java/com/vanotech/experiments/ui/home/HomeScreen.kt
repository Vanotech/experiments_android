package com.vanotech.experiments.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.vanotech.experiments.R


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
                    Text(text = stringResource(R.string.route_home))
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        HomeContent(
            navController = navController,
            viewModel = viewModel,
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun HomeContent(
    navController: NavController,
    viewModel: HomeViewModel,
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
        val destinations = viewModel.items
        items(
            count = destinations.size,
        ) { index ->
            HomeItem(
                destination = destinations[index],
                navController = navController
            )
        }
    }
}