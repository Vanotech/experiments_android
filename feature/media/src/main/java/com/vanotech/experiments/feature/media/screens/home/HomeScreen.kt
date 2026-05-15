package com.vanotech.experiments.feature.media.screens.home

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.vanotech.experiments.feature.media.R
import com.vanotech.experiments.feature.media.screens.home.components.MediaFeed
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    onViewRequest: (MediaUiModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val items = viewModel.media.collectAsLazyPagingItems()

    HomeScreen(
        items = items,
        onViewRequest = onViewRequest,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun HomeScreen(
    items: LazyPagingItems<MediaUiModel>,
    onViewRequest: (MediaUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_media_home))
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { contentPadding ->
        val isRefreshing = items.loadState.refresh == LoadState.Loading
        val isNotEmpty = items.itemCount > 0

        if (isRefreshing || isNotEmpty) {
            MediaFeed(
                items = items,
                onItemClick = onViewRequest,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InfoContent(
                text = stringResource(R.string.label_no_media),
                modifier = Modifier.padding(contentPadding),
            )
        }
    }
}
