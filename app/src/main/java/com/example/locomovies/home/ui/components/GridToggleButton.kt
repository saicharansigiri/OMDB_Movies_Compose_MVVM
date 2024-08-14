package com.example.locomovies.home.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.locomovies.R

@Composable
fun GridToggleButton(
    isGridMode: Boolean,
    onToggle: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.list_to_grid))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        reverseOnRepeat = false,
        isPlaying = true,
        speed = if (isGridMode) 1f else -1f
    )
    val shape = RoundedCornerShape(24.dp)

    Surface(
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clip(shape)
            .clickable {
                onToggle.invoke()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            AnimatedContent(
                targetState = isGridMode,
                transitionSpec = {
                    slideInVertically(animationSpec = tween(durationMillis = 300)) { height -> height } togetherWith
                            slideOutVertically(animationSpec = tween(durationMillis = 300)) { height -> -height } using
                            SizeTransform(clip = false)
                }, label = ""
            ) { targetState ->
                // Content to be animated
                if (targetState) {
                    Text("Grid")
                } else {
                    Text("List")
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}

