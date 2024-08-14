package com.example.locomovies.home.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.locomovies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    Surface(
        shape = RoundedCornerShape(48),
        color = MaterialTheme.colorScheme.surfaceVariant, // Customize color if needed
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant // Customize color if needed
            )
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearch(it)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Search...") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(onSearch = {})
}