package com.example.locomovies.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.locomovies.data.model.Movie
import com.example.locomovies.home.ui.components.GridMovieItem
import com.example.locomovies.home.ui.components.GridToggleButton
import com.example.locomovies.home.ui.components.ListMovieItem
import com.example.locomovies.home.ui.components.SearchBar
import com.example.locomovies.home.ui.components.SortButton
import com.example.locomovies.home.ui.theme.LocoMoviesTheme
import com.example.locomovies.home.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        var isGridMode by remember { mutableStateOf(true) }

        LocoMoviesTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    SearchBar(modifier = Modifier.padding(8.dp), onSearch = {})
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        GridToggleButton(
                            isGridMode = isGridMode,
                            onToggle = {
                                isGridMode = !isGridMode
                            },
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        SortButton()
                    }
                    ShowMoviesListUI(isGridMode)
                }
            }
        }
    }

    @Composable
    fun ShowMoviesListUI(isGridMode: Boolean) {
        val list = viewModel.moviesList.observeAsState()

        LaunchedEffect(Unit) {
            viewModel.getMoviesList("batman")
        }

        AnimatedContent(
            targetState = isGridMode,
            label = ""
        ) { targetState ->
            // Content to be animated
            if (targetState) {
                MovieGridView(list.value ?: emptyList())
            } else {
                MovieListView(list.value ?: emptyList())
            }
        }
    }

    @Composable
    fun MovieGridView(movies: List<Movie>) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(movies) { movie ->
                GridMovieItem(movie)
            }
        }
    }

    @Composable
    fun MovieListView(movies: List<Movie>) {
        LazyColumn {
            items(movies) { movie ->
                ListMovieItem(movie)
            }
        }
    }


}
