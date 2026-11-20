package com.example.hava.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.data.remote.CurrentWeather
import com.example.hava.domain.WeatherCondition
import kotlin.math.roundToInt
import com.example.hava.ui.theme.HavaNavy
import com.example.hava.ui.theme.HavaNightCard
import com.example.hava.ui.theme.HavaTextLight
import com.example.hava.ui.theme.HeroChipDay
import androidx.compose.ui.res.stringResource
import com.example.hava.R

@Composable
fun WeatherHero(
    cityName: String,
    weather: CurrentWeather?,
    isLoading: Boolean,
    errorMessage: String?,
    onCityClick: () -> Unit
) {
    val isNight = weather?.isDay == 0
    val textColor = if (isNight) HavaTextLight else HavaNavy
    val chipColor = if (isNight) HavaNightCard else HeroChipDay

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TextButton(
            onClick = onCityClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = textColor,
                containerColor = chipColor
            )
        ) {
            Text(
                text = when {
                    cityName.isNotBlank() -> stringResource(R.string.hero_city_chip, cityName)
                    isLoading -> stringResource(R.string.hero_locating)
                    else -> stringResource(R.string.hero_pick_city)
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        when {
            isLoading -> {
                Column(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Text(stringResource(R.string.hero_loading), color = textColor)
                }
            }

            weather == null -> {
                Column(
                    modifier = Modifier.padding(top = 12.dp, bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WeatherIcon(
                        condition = WeatherCondition.PARTLY_CLOUDY,
                        night = false,
                        modifier = Modifier.size(92.dp)
                    )
                    Text(
                        text = errorMessage ?: stringResource(R.string.hero_empty_hint),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = textColor,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                        Text(
                            text = "${weather.temperature.roundToInt()}°",
                            color = textColor,
                            fontSize = 70.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 74.sp,
                            letterSpacing = (-2).sp
                        )
                        Text(
                            text = weatherDescription(weather.weatherCode),
                            color = textColor,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = stringResource(R.string.hero_feels_like, weather.feelsLike.roundToInt()),
                            color = textColor.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    WeatherIcon(
                        condition = WeatherCondition.fromCode(weather.weatherCode),
                        night = isNight,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(118.dp)
                    )
                }
            }
        }
    }
}
