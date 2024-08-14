package com.example.locomovies.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,
) : Parcelable

data class MoviesSearchResponse(
    @SerializedName("Search")val list: List<Movie>,
    @SerializedName("totalResults")val totalResults: String,
    @SerializedName("Response")val response: String,
)