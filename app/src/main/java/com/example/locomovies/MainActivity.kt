package com.example.locomovies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.locomovies.data.model.Movie
import com.example.locomovies.ui.components.GridMovieItem
import com.example.locomovies.ui.components.GridToggleButton
import com.example.locomovies.ui.components.ListMovieItem
import com.example.locomovies.ui.components.SearchBar
import com.example.locomovies.ui.components.SortButton
import com.example.locomovies.ui.theme.LocoMoviesTheme
import com.example.locomovies.viewmodel.MainViewModel
import com.example.locomovies.viewmodel.SortOrder
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
        val currentSortOrder = viewModel.sortOrder.collectAsState()

        LocoMoviesTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    SearchBar(modifier = Modifier.padding(8.dp), onSearch = {
                        viewModel.updateSearchQuery(it)
                    })
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
                        SortButton(currentSortOrder.value) {
                            viewModel.updateSortOrder(it)
                        }
                    }

                    AnimatedContent(
                        targetState = isGridMode,
                        label = ""
                    ) { targetState ->
                        MoviesListScreen(targetState)
                    }

                }
            }
        }
    }


    @Composable
    fun MoviesListScreen(isGridMode: Boolean) {
        val searchQuery = viewModel.searchQuery.collectAsState()

        if (searchQuery.value.isEmpty()) {
            EmptyListUI()
        } else {
            val movies = viewModel.movies.collectAsLazyPagingItems()
            when (movies.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                is LoadState.Error -> {
                    val error = movies.loadState.refresh as LoadState.Error
                    Log.e("LOCO", error.toString())
                }

                is LoadState.NotLoading -> {
                    if (movies.itemCount == 0) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "No Movies Found")
                        }
                    } else {
                        MoviesListContent(isGridMode, movies)
                    }
                }
            }
        }

    }

    @Composable
    fun EmptyListUI() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No Movies")
        }
    }

    @Composable
    fun MoviesListContent(
        isGridMode: Boolean,
        movies: LazyPagingItems<Movie>
    ) {

        val sortOrder = viewModel.sortOrder.collectAsState()

        val sortedMoviesList by remember {
            derivedStateOf {
                val unsortedList = movies.itemSnapshotList.items
                when (sortOrder.value) {
                    SortOrder.RATING_DESCENDING -> unsortedList.sortedByDescending { it.rating }
                    SortOrder.RATING_ASCENDING -> unsortedList.sortedBy { it.rating }
                    SortOrder.YEAR_DESCENDING -> unsortedList.sortedByDescending { it.year }
                    SortOrder.YEAR_ASCENDING -> unsortedList.sortedBy { it.year }
                }
            }
        }

        if (isGridMode) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(sortedMoviesList.size) { pos ->
                    movies[pos]?.let { GridMovieItem(movie = sortedMoviesList[pos]) }
                }

                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        } else {
            LazyColumn {
                items(sortedMoviesList.size) { pos ->
                    movies[pos]?.let { ListMovieItem(movie = sortedMoviesList[pos]) }
                }

                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }


}
