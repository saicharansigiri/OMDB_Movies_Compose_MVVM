package com.example.locomovies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.locomovies.R


@Composable
fun SortButton() {
    val isAscending = remember { mutableStateOf(true) }
    val selectedSortOption = remember { mutableStateOf("Ranking") }

    Row(modifier = Modifier.padding(8.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.weight(1f))
        Text("Sort by", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))

        MenuDropDown(
            selectedOption = selectedSortOption.value,
            onOptionSelected = { selectedSortOption.value = it }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = { isAscending.value = !isAscending.value }) {
            Icon(
                painter = painterResource(id = if (isAscending.value) R.drawable.ic_sort_ascending else R.drawable.ic_sort_descending),
                contentDescription = if (isAscending.value) "Ascending" else "Descending",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MenuDropDown(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    val menuItems = listOf("Year", "Ratings")

    Box {
        Row(
            modifier = Modifier
                .clickable { isExpanded.value = true }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedOption,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_drop_down_24),
                contentDescription = "Dropdown",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(text = {
                    Text(text = item, color = Color.Black)
                }, onClick = {
                    onOptionSelected(item)
                    isExpanded.value = false
                })
            }
        }
    }

}
