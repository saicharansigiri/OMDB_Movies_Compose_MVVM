package com.example.locomovies.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locomovies.data.model.Movie
import com.example.locomovies.data.network.OmdbApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: OmdbApiService) : ViewModel() {
    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>> = _moviesList

    private val _uiState = MutableStateFlow<MoviesUIState>(MoviesUIState.Loading)
    val uiState: StateFlow<MoviesUIState> = _uiState


    fun getMoviesList(searchQuery: String) {
        viewModelScope.launch {
            _uiState.value = MoviesUIState.Loading
            val movies = apiService.getMoviesList(searchQuery)
            _moviesList.value = movies.list
        }
    }


}


sealed class MoviesUIState {
    data object Loading : MoviesUIState()
    data class Success(val movies: List<Movie>) : MoviesUIState()
    data class Error(val message: String) : MoviesUIState()

}