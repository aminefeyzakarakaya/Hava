package com.example.hava.ui.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.hava.R
import com.example.hava.domain.WeatherCondition

@StringRes
fun weatherDescriptionRes(code: Int): Int = when (WeatherCondition.fromCode(code)) {
    WeatherCondition.CLEAR -> R.string.weather_clear
    WeatherCondition.MOSTLY_CLEAR -> R.string.weather_mostly_clear
    WeatherCondition.PARTLY_CLOUDY -> R.string.weather_partly_cloudy
    WeatherCondition.CLOUDY -> R.string.weather_cloudy
    WeatherCondition.FOG -> R.string.weather_fog
    WeatherCondition.DRIZZLE -> R.string.weather_drizzle
    WeatherCondition.RAIN -> R.string.weather_rain
    WeatherCondition.SNOW -> R.string.weather_snow
    WeatherCondition.THUNDERSTORM -> R.string.weather_thunderstorm
    WeatherCondition.UNKNOWN -> R.string.weather_unknown
}

/** Hava koduna karşılık gelen kısa açıklama (cihaz diline göre). */
@Composable
fun weatherDescription(code: Int): String = stringResource(weatherDescriptionRes(code))
