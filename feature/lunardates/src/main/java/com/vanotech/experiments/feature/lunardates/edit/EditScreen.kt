package com.vanotech.experiments.feature.lunardates.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vanotech.experiments.core.ui.NavigateBackButton
import com.vanotech.experiments.core.ui.SimpleExposedDropdownMenuBox
import com.vanotech.experiments.feature.lunardates.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditScreen(
    navController: NavController,
    viewModel: EditViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.route_lunar_dates_home))
                },
                navigationIcon = {
                    NavigateBackButton(navController = navController)
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.updateEvent()
                            navController.popBackStack()
                        },
                        enabled = viewModel.canUpdateEvent.collectAsState(initial = false).value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.action_save_date)
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteEvent()
                            navController.popBackStack()
                        },
                        enabled = viewModel.canDeleteEvent.collectAsState().value
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.action_delete_date)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = viewModel.title.collectAsState().value,
                onValueChange = {
                    viewModel.title.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.hint_title))
                }
            )

            SimpleExposedDropdownMenuBox(
                items = viewModel.daysOfMonths,
                selection = viewModel.dayOfMonth.collectAsState().value,
                onSelect = {
                    viewModel.dayOfMonth.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.hint_day))
                }
            )

            SimpleExposedDropdownMenuBox(
                items = viewModel.months,
                selection = viewModel.month.collectAsState().value,
                onSelect = {
                    viewModel.month.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(id = R.string.hint_month))
                }
            )
        }
    }
}
