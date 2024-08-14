package com.example.locomovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.locomovies.ui.components.MovieItem
import com.example.locomovies.ui.components.SearchBar
import com.example.locomovies.ui.components.SortButton
import com.example.locomovies.ui.theme.LocoMoviesTheme
import com.example.locomovies.viewmodel.MainViewModel
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
        LocoMoviesTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val list = viewModel.moviesList.observeAsState()
                LaunchedEffect(Unit) {
                    //viewModel.getMoviesList("batman")
                }
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    item{
                        SearchBar(modifier = Modifier.padding(8.dp), onSearch = {})
                    }
                    item{
                        SortButton()
                    }
                    list.value?.let {
                        items(it.size) { pos ->
                            MovieItem(movie = it[pos])
                        }
                    }
                }
            }
        }
    }


}
