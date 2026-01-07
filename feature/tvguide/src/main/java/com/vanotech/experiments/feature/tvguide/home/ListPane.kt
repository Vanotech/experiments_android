package com.vanotech.experiments.feature.tvguide.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.vanotech.experiments.core.ui.AspectRatio
import com.vanotech.experiments.data.tvguide.schema.Listing
import com.vanotech.experiments.feature.tvguide.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListPane(
    viewModel: HomeViewModel,
    isListAndDetailVisible: Boolean,
    onItemClick: (ListingUiModel) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showSettings by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_tv_guide_home))
                },
                actions = {
                    IconButton(
                        onClick = {
                            showSettings = !showSettings
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
        val uiState = viewModel.uiState.collectAsState().value
        val items = uiState.listings.collectAsLazyPagingItems()
        Feed(
            items = items,
            paddingValues = paddingValues,
            selectable = isListAndDetailVisible,
            isRefreshing = uiState.isRefreshing,
            onRefresh = viewModel::refreshListings,
            onItemClick = onItemClick
        )

        if (showSettings) {
            HomeSettingsBottomSheet(
                viewModel = viewModel,
                onDismissRequest = {
                    showSettings = false
                }
            )
        }
    }
}

@Composable
private fun Feed(
    items: LazyPagingItems<ListingUiModel>,
    paddingValues: PaddingValues,
    selectable: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onItemClick: (ListingUiModel) -> Unit
) {
    var selectedIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
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
                        selectable = selectable,
                        selected = index == selectedIndex
                    ) {
                        selectedIndex = index
                        onItemClick(item)
                    }
                } else {
                    PlaceHolder()
                }
            }
        }
    }
}

@Composable
private fun Item(
    item: ListingUiModel,
    selectable: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val interactionModifier = when (selectable) {
        true -> Modifier.selectable(
            selected = selected,
            onClick = onClick
        )

        false -> Modifier.clickable(onClick = onClick)
    }

    val borderStroke = when (selectable && selected) {
        true -> BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline
        )

        false -> null
    }

    Card(
        border = borderStroke,
        modifier = Modifier
            .then(interactionModifier)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageUrl)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(AspectRatio.WIDE_SCREEN),
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.5f))
                .padding(16.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.channelTitle,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.times(LocalContext.current),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview
@Composable
private fun ItemPreview() {
    val listing = Listing.mockData(0)
    val item = ListingUiModel(listing = listing)
    Item(item) { }
}

@Composable
private fun PlaceHolder() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(AspectRatio.WIDE_SCREEN)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun PlaceHolderPreview() {
    PlaceHolder()
}