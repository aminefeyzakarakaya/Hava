package com.example.hava.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.hava.domain.WeatherCondition
import com.example.hava.ui.theme.HavaNavy
import com.example.hava.ui.theme.HavaNightCard
import com.example.hava.ui.theme.HavaTextLight
import com.example.hava.ui.theme.MusicCardColors
import androidx.compose.ui.res.stringResource
import com.example.hava.R

data class WeatherSong(
    val title: String,
    val artist: String
)

/** Returns one named song suggestion based on the current weather and time of day. */
fun musicSuggestionForWeather(condition: WeatherCondition, isDay: Boolean): WeatherSong =
    when (condition) {
        WeatherCondition.DRIZZLE, WeatherCondition.RAIN, WeatherCondition.THUNDERSTORM ->
            WeatherSong("Yağmur Yağar", "Mutlak Sıfır")

        WeatherCondition.SNOW -> WeatherSong("Snowman", "Sia")

        WeatherCondition.CLEAR, WeatherCondition.MOSTLY_CLEAR -> if (isDay) {
            WeatherSong("Walking on Sunshine", "Katrina & The Waves")
        } else {
            WeatherSong("A Sky Full of Stars", "Coldplay")
        }

        WeatherCondition.PARTLY_CLOUDY -> if (isDay) {
            WeatherSong("Mr. Blue Sky", "Electric Light Orchestra")
        } else {
            WeatherSong("A Sky Full of Stars", "Coldplay")
        }

        WeatherCondition.CLOUDY, WeatherCondition.FOG -> WeatherSong("Clouds", "IYAMAH")

        WeatherCondition.UNKNOWN -> WeatherSong("Mr. Blue Sky", "Electric Light Orchestra")
    }

@Composable
fun WeatherMusicCard(
    song: WeatherSong,
    isNight: Boolean,
    modifier: Modifier = Modifier
) {
    val cardColor = if (isNight) HavaNightCard else MusicCardColors.CardDay
    val textColor = if (isNight) HavaTextLight else HavaNavy
    val accent = if (isNight) MusicCardColors.AccentNight else MusicCardColors.AccentDay

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

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(R.string.music_card_title),
                    color = textColor.copy(alpha = 0.68f),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = song.title,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = song.artist,
                    color = textColor.copy(alpha = 0.78f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
