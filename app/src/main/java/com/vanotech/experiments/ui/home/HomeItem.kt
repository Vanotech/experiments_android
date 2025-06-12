package com.vanotech.experiments.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
internal fun HomeItem(
    destination: HomeViewModel.Destination,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = destination.icon,
                contentDescription = destination.label
            )
            Text(
                text = destination.label,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
internal fun HomeItem(
    destination: HomeViewModel.Destination,
    navController: NavController
) {
    HomeItem(destination = destination) {
        navController.navigate(destination.route)
    }
}

@Preview
@Composable
fun HomeItemPreview() {
    val destination = HomeViewModel.Destination(
        icon = Icons.Default.Home,
        label = "Lorem ipsum",
        route = Unit
    )
    HomeItem(destination = destination) {}
}