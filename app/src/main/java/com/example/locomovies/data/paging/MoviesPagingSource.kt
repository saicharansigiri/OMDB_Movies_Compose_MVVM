package com.example.locomovies.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.locomovies.data.model.Movie
import com.example.locomovies.data.repository.MoviesRepository

class MoviesPagingSource(
    private val moviesRepository: MoviesRepository,
    private val searchQueryStr: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = moviesRepository.getMoviesListByPage(searchQueryStr, page)
            LoadResult.Page(
                data = response.list,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.list.isEmpty() || response.list.size < 10) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("SIGIRI", "ERROR $exception")
            LoadResult.Error(exception)
        }
    }
}