package com.example.locomovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locomovies.data.model.Movie
import com.example.locomovies.data.network.OmdbApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: OmdbApiService) : ViewModel() {
    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>> = _moviesList


    fun getMoviesList(searchQuery: String) {
        viewModelScope.launch {
            delay(5000)
            val movies = apiService.getMoviesList(searchQuery)
            _moviesList.value = movies.list
        }
    }



}