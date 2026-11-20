package com.example.hava.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.data.remote.CurrentWeather
import com.example.hava.domain.WeatherCondition
import com.example.hava.ui.components.DetailType
import com.example.hava.ui.components.WeatherDetailIcon
import com.example.hava.ui.components.weatherDescription
import com.example.hava.ui.theme.weatherCardColor
import com.example.hava.ui.theme.weatherTextColor
import kotlin.math.roundToInt
import androidx.compose.ui.res.stringResource
import com.example.hava.R

@Composable
fun TodayDetails(weather: CurrentWeather, isNight: Boolean) {
    val condition = WeatherCondition.fromCode(weather.weatherCode)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.details_title),
            modifier = Modifier.fillMaxWidth(),
            color = weatherTextColor(isNight),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailCard(
                type = DetailType.HUMIDITY,
                title = stringResource(R.string.detail_humidity),
                value = stringResource(R.string.humidity_value, weather.humidity),
                isNight = isNight,
                condition = condition,
                modifier = Modifier.weight(1f)
            )
            DetailCard(
                type = DetailType.WIND,
                title = stringResource(R.string.detail_wind),
                value = stringResource(R.string.wind_value, weather.windSpeed.roundToInt()),
                isNight = isNight,
                condition = condition,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailCard(
                type = DetailType.FEELS_LIKE,
                title = stringResource(R.string.detail_feels_like),
                value = "${weather.feelsLike.roundToInt()}°",
                isNight = isNight,
                condition = condition,
                modifier = Modifier.weight(1f)
            )
            DetailCard(
                type = DetailType.CONDITIONS,
                title = stringResource(R.string.detail_conditions),
                value = weatherDescription(weather.weatherCode),
                isNight = isNight,
                condition = condition,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DetailCard(
    type: DetailType,
    title: String,
    value: String,
    isNight: Boolean,
    condition: WeatherCondition,
    modifier: Modifier = Modifier
) {
    val textColor = weatherTextColor(isNight)

    Surface(
        modifier = modifier.heightIn(min = 106.dp),
        shape = RoundedCornerShape(20.dp),
        color = weatherCardColor(condition, isNight)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WeatherDetailIcon(type = type, description = title, night = isNight)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title,
                    color = textColor.copy(alpha = 0.82f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = value,
                    color = textColor,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
