package com.example.locomovies.data.network

import com.example.locomovies.data.model.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbApiService {

    companion object {
        const val SEARCH_QUERY = "s"
        const val PAGE_QUERY = "page"
    }

    @GET(".")
    suspend fun getMoviesList(
        @Query(SEARCH_QUERY) searchQuery: String,
    ): MoviesSearchResponse


    @GET(".")
    suspend fun getMoviesListByPage(
        @Query(SEARCH_QUERY) searchQuery: String,
        @Query(PAGE_QUERY) pageNumber: Int,
    ): MoviesSearchResponse

}