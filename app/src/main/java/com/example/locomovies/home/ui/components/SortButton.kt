package com.example.locomovies.home.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.locomovies.R
import com.example.locomovies.home.viewmodel.SortOrder


@Composable
fun SortButton(
    currentSortOrder: SortOrder,
    onSortOrderChanged: (SortOrder) -> Unit
) {

    var isAscending by remember {
        mutableStateOf(
            currentSortOrder == SortOrder.RATING_ASCENDING || currentSortOrder == SortOrder.YEAR_ASCENDING
        )
    }
    var selectedSortOption by remember {
        mutableStateOf(
            if (currentSortOrder.name.contains("RATING")) SortMenu.RATING else SortMenu.YEAR
        )
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Sort by", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))

        MenuDropDown(
            selectedSortOption,
            onOptionSelected = {
                selectedSortOption = it
                when (it) {
                    SortMenu.RATING -> {
                        if (isAscending) {
                            onSortOrderChanged.invoke(SortOrder.RATING_ASCENDING)
                        } else {
                            onSortOrderChanged.invoke(SortOrder.RATING_DESCENDING)
                        }
                    }

                    SortMenu.YEAR -> {
                        if (isAscending) {
                            onSortOrderChanged.invoke(SortOrder.YEAR_ASCENDING)
                        } else {
                            onSortOrderChanged.invoke(SortOrder.YEAR_DESCENDING)
                        }
                    }
                }

            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {
            isAscending = !isAscending
            when (selectedSortOption) {
                SortMenu.RATING -> {
                    if (isAscending) {
                        onSortOrderChanged.invoke(SortOrder.RATING_ASCENDING)
                    } else {
                        onSortOrderChanged.invoke(SortOrder.RATING_DESCENDING)
                    }
                }

                SortMenu.YEAR -> {
                    if (isAscending) {
                        onSortOrderChanged.invoke(SortOrder.YEAR_ASCENDING)
                    } else {
                        onSortOrderChanged.invoke(SortOrder.YEAR_DESCENDING)
                    }
                }
            }
        }) {
            Icon(
                painter = painterResource(id = if (isAscending) R.drawable.ic_sort_ascending else R.drawable.ic_sort_descending),
                contentDescription = if (isAscending) "Ascending" else "Descending",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MenuDropDown(selectedSortOption: SortMenu, onOptionSelected: (SortMenu) -> Unit) {
    val isExpanded = remember { mutableStateOf(false) }
    val menuItems: List<SortMenu> = listOf(SortMenu.YEAR, SortMenu.RATING)

    Box {
        Row(
            modifier = Modifier
                .clickable { isExpanded.value = true }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedSortOption.name,
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
                    Text(text = item.name, color = Color.Black)
                }, onClick = {
                    onOptionSelected(item)
                    isExpanded.value = false
                })
            }
        }
    }

}


enum class SortMenu {
    RATING,
    YEAR
}