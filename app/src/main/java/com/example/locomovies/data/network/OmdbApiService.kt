package com.example.locomovies.data.network

import com.example.locomovies.data.model.Movie
import retrofit2.http.GET


interface OmdbApiService {

    companion object{

    }

    @GET()
    fun getMoviesList(): List<Movie>


}