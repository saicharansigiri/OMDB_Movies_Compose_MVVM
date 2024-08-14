package com.example.locomovies.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.locomovies.R
import com.example.locomovies.data.model.Movie

@Composable
fun GridMovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = movie.poster,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CapsuleText(text = movie.year.substring(0..3), backgroundColor = Color.White)
                CapsuleText(text = "8.9", icon = R.drawable.star_ic, backgroundColor = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
