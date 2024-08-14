package com.example.locomovies.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CapsuleText(
    text: String,
    icon: Int? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconColor: Color = Color(0xFFFAC333)
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = backgroundColor,
        contentColor = contentColor,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp).size(16.dp),
                    tint = iconColor
                )
            }
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview
@Composable
fun CapsuleTextPreview() {
    CapsuleText(text = "Capsule Text")
}