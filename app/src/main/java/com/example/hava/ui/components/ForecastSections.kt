package com.example.hava.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.data.remote.WeatherResponse
import com.example.hava.domain.WeatherCondition
import com.example.hava.ui.theme.weatherCardColor
import com.example.hava.ui.theme.weatherTextColor
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt
import com.example.hava.ui.theme.WeatherIconColors
import androidx.compose.ui.res.stringResource
import com.example.hava.R

private fun cardColors(isNight: Boolean, code: Int): Pair<Color, Color> =
    weatherCardColor(WeatherCondition.fromCode(code), isNight) to weatherTextColor(isNight)

// Açık renkli kartların üstünde bulut görünsün diye bulut rengi biraz koyu.
private fun cardCloudColor(isNight: Boolean): Color =
    if (isNight) WeatherIconColors.ForecastCloudNight else WeatherIconColors.ForecastCloudDay

@Composable
fun HourlyForecast(weather: WeatherResponse, isNight: Boolean) {
    val hourly = weather.hourly
    val count = minOf(
        hourly.time.size, hourly.temperatures.size,
        hourly.weatherCodes.size, hourly.dayFlags.size
    )
    val currentHour = weather.current.time.take(13) // YYYY-MM-DDTHH
    val firstIndex = hourly.time.indexOfFirst { it.take(13) >= currentHour }
        .let { if (it < 0) 0 else it }
    val indices = (firstIndex until minOf(firstIndex + 12, count)).toList()
    if (indices.isEmpty()) return

    val (cardColor, textColor) = cardColors(isNight, weather.current.weatherCode)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            stringResource(R.string.forecast_hourly),
            color = if (isNight) Color.White else textColor,
            style = MaterialTheme.typography.titleMedium
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(end = 18.dp)
        ) {
            items(indices) { index ->
                val isNightHour = hourly.dayFlags[index] == 0
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = cardColor,
                    modifier = Modifier.width(84.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = if (index == firstIndex) stringResource(R.string.now)
                            else hourly.time[index].takeLast(5),
                            color = textColor.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                        WeatherIcon(
                            condition = WeatherCondition.fromCode(hourly.weatherCodes[index]),
                            night = isNightHour,
                            modifier = Modifier.size(46.dp),
                            cloudColor = cardCloudColor(isNightHour)
                        )
                        Text(
                            "${hourly.temperatures[index].roundToInt()}°",
                            color = textColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyForecast(weather: WeatherResponse, isNight: Boolean) {
    val daily = weather.daily
    val count = minOf(
        daily.time.size, daily.weatherCodes.size,
        daily.maxTemperatures.size, daily.minTemperatures.size, 7
    )
    if (count == 0) return

    val (cardColor, textColor) = cardColors(isNight, weather.current.weatherCode)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            stringResource(R.string.forecast_daily),
            color = if (isNight) Color.White else textColor,
            style = MaterialTheme.typography.titleMedium
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(end = 18.dp)
        ) {
            items((0 until count).toList()) { index ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = cardColor,
                    modifier = Modifier.width(118.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = if (index == 0) stringResource(R.string.today) else dayName(daily.time[index]),
                            color = textColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        WeatherIcon(
                            condition = WeatherCondition.fromCode(daily.weatherCodes[index]),
                            night = false,
                            modifier = Modifier.size(46.dp),
                            cloudColor = cardCloudColor(false)
                        )
                        Text(
                            text = weatherDescription(daily.weatherCodes[index]),
                            color = textColor.copy(alpha = 0.85f),
                            fontSize = 11.sp,
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                "${daily.maxTemperatures[index].roundToInt()}°",
                                color = textColor,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "${daily.minTemperatures[index].roundToInt()}°",
                                color = textColor.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun dayName(date: String): String = try {
    val parsed = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)
    if (parsed == null) date
    else SimpleDateFormat("EEE", Locale.forLanguageTag("tr-TR")).format(parsed)
        .replaceFirstChar { it.uppercaseChar() }
} catch (_: Exception) {
    date
}
