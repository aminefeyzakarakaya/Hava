package com.example.hava.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.hava.domain.WeatherCondition

/** Detay ve tahmin kartlarının arka plan rengi. */
fun weatherCardColor(condition: WeatherCondition, night: Boolean): Color = when {
    night -> HavaNightCard
    condition.isRainy -> WeatherCardColors.Rain
    condition.isSnowy -> WeatherCardColors.Snow
    condition.isOvercast -> WeatherCardColors.Overcast
    condition == WeatherCondition.PARTLY_CLOUDY -> WeatherCardColors.PartlyCloudy
    else -> WeatherCardColors.Clear
}

fun weatherTextColor(night: Boolean): Color =
    if (night) HavaTextLight else HavaNavy
