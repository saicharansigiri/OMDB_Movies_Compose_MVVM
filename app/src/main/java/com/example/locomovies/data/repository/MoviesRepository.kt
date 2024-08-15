package com.example.locomovies.data.repository

import com.example.locomovies.data.model.MoviesSearchResponse
import com.example.locomovies.data.network.OmdbApiService
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

class MoviesRepository @Inject constructor(private val apiService: OmdbApiService) {

    suspend fun getMoviesList(searchQuery: String): MoviesSearchResponse {
        val response = apiService.getMoviesList(searchQuery)
        return response.copy(
            list = response.list.map { movie ->
                val formattedRating = (Random.nextDouble(1.0, 10.0) * 10).roundToInt() / 10.0
                movie.copy(rating = formattedRating)
            }
        )
    }

    suspend fun getMoviesListByPage(searchQuery: String, page: Int): MoviesSearchResponse {
        val response = apiService.getMoviesListByPage(searchQuery, page)

        return if (response.response == "True") {
            response.copy(
                list = response.list.map { movie ->
                    val formattedRating = (Random.nextDouble(1.0, 10.0) * 10).roundToInt() / 10.0
                    movie.copy(rating = formattedRating)
                }
            )
        } else {
            MoviesSearchResponse(
                list = emptyList(),
                totalResults = "0",
                response = "True"
            )
        }

    }

}