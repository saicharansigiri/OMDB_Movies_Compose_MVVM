package com.example.locomovies.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val imdbId: String,
    val title: String,
    val year: String,
    val type: String,
    val poster: String,
) : Parcelable
