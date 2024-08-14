package com.example.locomovies.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.locomovies.R
import com.example.locomovies.data.model.Movie

@Composable
fun MovieItem(movie: Movie) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = movie.title)
                Row {
                    CapsuleText(text = movie.year, backgroundColor = Color.White)
                    CapsuleText(text = "8.9", icon = R.drawable.star_ic, backgroundColor = Color.White)
                }

            }
        }
    }
}


@Composable
@Preview
fun PreviewItem() {
    val movie = Movie(
        imdbId = "tt0372784",
        title = "Batman Begins",
        year = "2005",
        type = "movie",
        poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
    )
    MovieItem(movie = movie)
}