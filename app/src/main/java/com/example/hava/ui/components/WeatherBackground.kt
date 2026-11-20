package com.example.hava.ui.components

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
import com.example.hava.data.remote.CurrentWeather
import com.example.hava.domain.WeatherCondition
import com.example.hava.ui.theme.NightStarWarm
import com.example.hava.ui.theme.WeatherGradients

@Composable
fun WeatherBackground(
    weather: CurrentWeather?,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(backgroundColors(weather)))
    ) {
        if (weather?.isDay == 0) NightStars()
        content()
    }
}

private fun backgroundColors(weather: CurrentWeather?): List<Color> {
    if (weather == null) return WeatherGradients.Default

    val condition = WeatherCondition.fromCode(weather.weatherCode)
    val night = weather.isDay == 0
    val cloudyNight = condition == WeatherCondition.PARTLY_CLOUDY || condition.isOvercast

    return when {
        night && condition.isSnowy -> WeatherGradients.NightSnow
        night && condition.isRainy -> WeatherGradients.NightRain
        night && cloudyNight -> WeatherGradients.NightCloudy
        night -> WeatherGradients.Night
        condition.isSnowy -> WeatherGradients.Snow
        condition.isRainy -> WeatherGradients.Rain
        condition.isOvercast -> WeatherGradients.Overcast
        condition == WeatherCondition.PARTLY_CLOUDY -> WeatherGradients.PartlyCloudy
        else -> WeatherGradients.Clear
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
                color = if (index % 6 == 0) NightStarWarm else Color.White,
                radius = star.radiusDp.dp.toPx(),
                center = Offset(size.width * star.x, size.height * star.y),
                alpha = twinkleAlpha
            )
        }
    }
}

private data class Star(val x: Float, val y: Float, val radiusDp: Float)
