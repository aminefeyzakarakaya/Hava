package com.example.hava.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hava.data.CurrentWeather

@Composable
fun WeatherBackground(
    hava: CurrentWeather?,
    content: @Composable () -> Unit
) {
    val gece = hava?.isDay == 0
    val kod = hava?.weatherCode ?: -1
    val colors = when {
        hava == null -> listOf(Color(0xFFB7DEFA), Color(0xFFE7E8FF))
        gece && (kod in 71..77 || kod == 85 || kod == 86) ->
            listOf(Color(0xFF132A49), Color(0xFF243C5C))
        gece && (kod in 51..67 || kod in 80..82 || kod in 95..99) ->
            listOf(Color(0xFF0B203B), Color(0xFF1B3657))
        gece && (kod in 2..3 || kod in 45..48) ->
            listOf(Color(0xFF0D2341), Color(0xFF233755))
        gece -> listOf(Color(0xFF081A35), Color(0xFF172E50))
        kod in 71..77 || kod == 85 || kod == 86 ->
            listOf(Color(0xFFB9DDF4), Color(0xFFEAF6FF))
        kod in 51..67 || kod in 80..82 || kod in 95..99 ->
            listOf(Color(0xFF8DBEDB), Color(0xFFD5E7F2))
        kod == 3 || kod in 45..48 ->
            listOf(Color(0xFFB7CDE2), Color(0xFFE4EAF4))
        kod == 2 -> listOf(Color(0xFF80BCEC), Color(0xFFD9E6FA))
        else -> listOf(Color(0xFF76BDF1), Color(0xFFFFE6AD))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors))
    ) {
        if (gece) NightStars()
        content()
    }
}

@Composable
private fun NightStars() {
    val transition = rememberInfiniteTransition(label = "night-stars")
    val twinkle by transition.animateFloat(
        initialValue = 0.36f,
        targetValue = 0.92f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star-twinkle"
    )

    // Small stars stay behind the content, with a few brighter points around the hero.
    val stars = listOf(
        Star(.08f, .06f, 1.8f), Star(.21f, .11f, 1.1f), Star(.39f, .05f, 1.2f),
        Star(.56f, .10f, 2.0f), Star(.73f, .06f, 1.1f), Star(.91f, .12f, 1.7f),
        Star(.14f, .22f, 1.0f), Star(.32f, .19f, 1.7f), Star(.48f, .24f, 1.1f),
        Star(.67f, .20f, 1.2f), Star(.84f, .26f, 1.9f), Star(.96f, .34f, 1.0f),
        Star(.07f, .39f, 1.3f), Star(.25f, .32f, 1.0f), Star(.44f, .38f, 1.8f),
        Star(.62f, .34f, 1.0f), Star(.78f, .41f, 1.2f), Star(.91f, .47f, 1.7f),
        Star(.17f, .51f, 1.1f), Star(.36f, .48f, 1.5f), Star(.55f, .54f, 1.0f),
        Star(.72f, .51f, 1.8f), Star(.04f, .61f, 1.0f), Star(.88f, .64f, 1.1f),
        Star(.27f, .69f, 1.6f), Star(.49f, .66f, 1.0f), Star(.66f, .74f, 1.3f)
    )

    Canvas(Modifier.fillMaxSize()) {
        stars.forEachIndexed { index, star ->
            val twinkleAlpha = if (index % 4 == 0) twinkle else 0.56f
            drawCircle(
                color = if (index % 6 == 0) Color(0xFFFFE5A3) else Color.White,
                radius = star.radiusDp.dp.toPx(),
                center = Offset(size.width * star.x, size.height * star.y),
                alpha = twinkleAlpha
            )
        }
    }
}

private data class Star(val x: Float, val y: Float, val radiusDp: Float)
