package com.example.hava.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class WeatherMusicSuggestion(
    val title: String,
    val subtitle: String
)

/** Returns a listening mood; the user-curated songs can be attached to these moods later. */
fun musicSuggestionForWeather(weatherCode: Int, isDay: Boolean): WeatherMusicSuggestion = when {
    weatherCode in 51..67 || weatherCode in 80..82 || weatherCode in 95..99 ->
        WeatherMusicSuggestion("Yağmur eşlikçisi", "Sakin, yumuşak parçalar")

    weatherCode in 71..77 || weatherCode == 85 || weatherCode == 86 ->
        WeatherMusicSuggestion("Kar manzarası", "Dingin parçalarla sıcacık bir mola")

    weatherCode == 0 || weatherCode == 1 -> if (isDay) {
        WeatherMusicSuggestion("Güneşli yürüyüş", "Enerjik parçalar için güzel bir gün")
    } else {
        WeatherMusicSuggestion("Yıldızlı gece", "Geceye yakışan sakin parçalar")
    }

    weatherCode == 2 -> if (isDay) {
        WeatherMusicSuggestion("Hafif enerjik", "Parçalı bulutlara uygun bir yürüyüş")
    } else {
        WeatherMusicSuggestion("Bulutlu gece", "Rahatlatıcı, hafif ritimli parçalar")
    }

    weatherCode == 3 || weatherCode == 45 || weatherCode == 48 ->
        WeatherMusicSuggestion("Bulutlu hava modu", "Yumuşak ve keyifli parçalar")

    else -> WeatherMusicSuggestion("Bugünün ritmi", "Havaya uygun bir müzik molası")
}

@Composable
fun WeatherMusicCard(
    suggestion: WeatherMusicSuggestion,
    isNight: Boolean,
    modifier: Modifier = Modifier
) {
    val cardColor = if (isNight) Color(0xFF193655) else Color(0xFFDCE9F8)
    val textColor = if (isNight) Color(0xFFF8FAFF) else Color(0xFF19365B)
    val accent = if (isNight) Color(0xFFC6B9FF) else Color(0xFFFFC4B2)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = cardColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♫",
                    color = textColor,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            androidx.compose.foundation.layout.Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "HAVANIN RİTMİ",
                    color = textColor.copy(alpha = 0.68f),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp
                )
                Text(
                    text = suggestion.title,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = suggestion.subtitle,
                    color = textColor.copy(alpha = 0.78f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
