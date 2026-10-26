package com.example.hava.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = HavaBlueLight,
    onPrimary = HavaNavy,
    secondary = HavaSun,
    onSecondary = HavaNavy,
    tertiary = HavaLilac,
    background = HavaNight,
    onBackground = HavaTextLight,
    surface = HavaNight,
    onSurface = HavaTextLight,
    surfaceVariant = HavaNightCard,
    onSurfaceVariant = Color(0xFFD8E4F5)
)

private val LightColorScheme = lightColorScheme(
    primary = HavaBlue,
    onPrimary = Color.White,
    secondary = HavaCoral,
    onSecondary = HavaNavy,
    tertiary = HavaMint,
    background = Color(0xFFF3F8FF),
    onBackground = HavaNavy,
    surface = Color(0xFFFFFEFC),
    onSurface = HavaNavy,
    surfaceVariant = HavaSky,
    onSurfaceVariant = Color(0xFF536A86)
)

@Composable
fun HavaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
