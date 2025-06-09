package com.vanotech.experiments.core.ui

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenuTextField(
    items: List<T>,
    selection: T,
    onSelect: (T) -> Unit,
    itemText: (T) -> String,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
) {
    var expand by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expand,
        onExpandedChange = { expand = it }
    ) {
        TextField(
            value = itemText(selection),
            onValueChange = {},
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = label,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = itemText(item)) },
                    onClick = {
                        onSelect(item)
                        expand = false
                    }
                )
            }
        }
    }
}