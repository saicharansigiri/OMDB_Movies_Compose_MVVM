package com.example.locomovies.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.locomovies.data.model.Movie
import com.example.locomovies.data.paging.MoviesPagingSource
import com.example.locomovies.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.RATING_DESCENDING)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Flow of PagingData that is tied to the current search query
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = _searchQuery
        .debounce(300)
        .filter { it.length >= 3 }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getSearchResultStream(query)
        }.cachedIn(viewModelScope)

    private fun getSearchResultStream(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviesPagingSource(moviesRepository, query) }
        ).flow
    }

}


enum class SortOrder {
    RATING_DESCENDING,
    RATING_ASCENDING,
    YEAR_DESCENDING,
    YEAR_ASCENDING
}