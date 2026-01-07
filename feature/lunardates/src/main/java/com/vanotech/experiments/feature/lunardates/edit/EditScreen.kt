package com.vanotech.experiments.feature.lunardates.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.vanotech.experiments.core.ui.BackButton
import com.vanotech.experiments.core.ui.DropdownMenuTextField
import com.vanotech.experiments.feature.lunardates.R
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditScreen(
    navController: NavController,
    viewModel: EditViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    val title = when (uiState.createOnly) {
                        true -> R.string.route_lunar_dates_add
                        else -> R.string.route_lunar_dates_edit
                    }
                    Text(text = stringResource(title))
                },
                navigationIcon = {
                    BackButton(navController = navController)
                },
                actions = {
                    if (!uiState.createOnly) {
                        IconButton(
                            onClick = {
                                viewModel.deleteEvent()
                                navController.popBackStack()
                            },
                            enabled = uiState.canDeleteEvent
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.action_delete_date)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        EditContent(
            navController = navController,
            viewModel = viewModel,
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun EditContent(
    navController: NavController,
    viewModel: EditViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val uiState = viewModel.uiState.collectAsState().value
        val initialUiState  = remember(viewModel) { viewModel.uiState.value }

        val titleState = rememberTextFieldState(initialUiState .title)
        val titleKeyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        LaunchedEffect(titleState) {
            snapshotFlow {
                titleState.text.toString()
            }.collectLatest {
                viewModel.updateTitle(it)
            }
        }
        TextField(
            state = titleState,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.hint_title))
            },
            keyboardOptions = titleKeyboardOptions
        )

        DropdownMenuTextField(
            items = uiState.daysOfMonth,
            selection = uiState.dayOfMonth,
            onSelect = {
                viewModel.updateDayOfMonth(it)
            },
            itemText = { it.label },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.hint_day))
            }
        )

        DropdownMenuTextField(
            items = uiState.months,
            selection = uiState.month,
            onSelect = {
                viewModel.updateMonth(it)
            },
            itemText = { it.label },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.hint_month))
            }
        )

        Button(
            onClick = {
                viewModel.updateEvent()
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End),
            enabled = uiState.canUpdateEvent
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.action_save_date)
            )
        }
    }
}