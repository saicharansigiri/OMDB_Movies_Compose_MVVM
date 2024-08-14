package com.example.locomovies.data.network

import com.example.locomovies.data.model.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OmdbApiService {

    @GET(".")
    suspend fun getMoviesList(
        @Query("s") searchQuery: String,
    ): MoviesSearchResponse


}