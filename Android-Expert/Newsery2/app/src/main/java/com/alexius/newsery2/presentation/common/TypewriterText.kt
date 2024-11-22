package com.alexius.newsery2.presentation.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.alexius.newsery2.R

@Composable
fun TypewriterText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
    fontSize: TextUnit = 12.sp,
    color: Color = colorResource(id = R.color.text_title),
    typewriterSpeed: Long = 100L // Speed in milliseconds
) {
    var displayedText by remember { mutableStateOf("") }
    val animatable = remember { Animatable(0f) }

    LaunchedEffect(text) {
        animatable.snapTo(0f)
        animatable.animateTo(
            targetValue = text.length.toFloat(),
            animationSpec = tween(durationMillis = (text.length * typewriterSpeed).toInt())
        )
        displayedText = text.substring(0, animatable.value.toInt())
    }

    LaunchedEffect(animatable.value) {
        displayedText = text.substring(0, animatable.value.toInt())
    }

    Text(
        text = displayedText,
        modifier = modifier,
        fontSize = fontSize,
        color = color,
        style = style
    )
}