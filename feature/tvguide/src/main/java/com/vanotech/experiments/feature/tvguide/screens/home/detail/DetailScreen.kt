package com.vanotech.experiments.feature.tvguide.screens.home.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vanotech.experiments.core.ui.components.BackButton
import com.vanotech.experiments.core.ui.components.InfoContent
import com.vanotech.experiments.data.tvguide.Listing
import com.vanotech.experiments.feature.tvguide.screens.home.detail.components.DetailContent

@Composable
internal fun DetailScreen(
    args: DetailRoute,
    isExpandedLayout: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = DetailViewModel.viewModel(args)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreen(
        listing = uiState.listing,
        isExpandedLayout = isExpandedLayout,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreen(
    listing: Listing?,
    isExpandedLayout: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    if (!isExpandedLayout) {
                        BackButton(onClick = onDismissRequest)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { contentPadding ->
        when (listing) {
            null -> {
                InfoContent(
                    title = null,
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                )
            }

            else -> {
                DetailContent(
                    listing = listing,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }
    }
}
