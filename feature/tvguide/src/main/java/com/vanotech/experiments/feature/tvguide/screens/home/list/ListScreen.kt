package com.vanotech.experiments.feature.tvguide.screens.home.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vanotech.experiments.core.ui.components.InfoContent
import com.vanotech.experiments.feature.tvguide.R
import com.vanotech.experiments.feature.tvguide.screens.home.list.components.ListingFeed

@Composable
internal fun ListScreen(
    onSettingsRequest: () -> Unit,
    onViewRequest: (ListingUiModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = ListViewModel.viewModel()
) {
    val items = viewModel.listings.collectAsLazyPagingItems()

    ListScreen(
        items = items,
        onSettingsRequest = onSettingsRequest,
        onViewRequest = onViewRequest,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    items: LazyPagingItems<ListingUiModel>,
    onSettingsRequest: () -> Unit,
    onViewRequest: (ListingUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_tv_guide_home))
                },
                actions = {
                    IconButton(
                        onClick = {
                            onSettingsRequest()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = stringResource(R.string.route_tv_guide_settings)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        val isRefreshing = items.loadState.refresh == LoadState.Loading
        val isNotEmpty = items.itemCount > 0

        if (isRefreshing || isNotEmpty) {
            ListingFeed(
                items = items,
                onItemClick = onViewRequest,
                isRefreshing = isRefreshing,
                onRefresh = { items.refresh() },
                modifier = Modifier.padding(paddingValues),
            )
        } else {
            InfoContent(
                title = stringResource(R.string.label_no_listings)
            )
        }
    }
}
