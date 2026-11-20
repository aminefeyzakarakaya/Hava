package com.example.hava.ui.theme

import androidx.compose.ui.graphics.Color

/** Ekran arka planı için yukarıdan aşağıya renk geçişleri. */
object WeatherGradients {
    val Default = listOf(Color(0xFFB7DEFA), Color(0xFFE7E8FF))

    val Clear = listOf(Color(0xFF76BDF1), Color(0xFFFFE6AD))
    val PartlyCloudy = listOf(Color(0xFF80BCEC), Color(0xFFD9E6FA))
    val Overcast = listOf(Color(0xFFB7CDE2), Color(0xFFE4EAF4))
    val Rain = listOf(Color(0xFF8DBEDB), Color(0xFFD5E7F2))
    val Snow = listOf(Color(0xFFB9DDF4), Color(0xFFEAF6FF))

    val Night = listOf(Color(0xFF081A35), Color(0xFF172E50))
    val NightCloudy = listOf(Color(0xFF0D2341), Color(0xFF233755))
    val NightRain = listOf(Color(0xFF0B203B), Color(0xFF1B3657))
    val NightSnow = listOf(Color(0xFF132A49), Color(0xFF243C5C))
}

/** Gece gökyüzündeki sıcak tonlu yıldızlar. */
val NightStarWarm = Color(0xFFFFE5A3)

/** Saatlik, haftalık ve detay kartlarının arka planı (gece için HavaNightCard kullanılır). */
object WeatherCardColors {
    val Clear = Color(0xFFE6F1FA)
    val PartlyCloudy = Color(0xFFDCEAF8)
    val Overcast = Color(0xFFE1E8F1)
    val Rain = Color(0xFFD2E4F0)
    val Snow = Color(0xFFE8F4FC)
}

/** WeatherIcon çizimindeki renkler. */
object WeatherIconColors {
    val Sun = Color(0xFFFFCF62)
    val Bolt = Color(0xFFFFD064)
    val Drizzle = Color(0xFF7CC3F0)
    val Rain = Color(0xFF58B6ED)
    val Snow = Color(0xFF73B9E8)
    val Fog = Color(0xFF9BBBD0)

    val CloudDay = Color(0xFFF8FCFF)
    val CloudNight = Color(0xFFE1ECFC)

    // Açık renkli kartların üstünde bulut görünsün diye daha koyu bulutlar
    val ForecastCloudDay = Color(0xFFCBD7E2)
    val ForecastCloudNight = Color(0xFFE3EBF7)
}

/** "Bugünün detayları" kartlarındaki yuvarlak ikon zeminleri. */
object DetailBadgeColors {
    val Humidity = Color(0xFFA9D5FF)
    val Wind = Color(0xFFA9DFED)
    val FeelsLike = Color(0xFFFFCDAA)
    val Conditions = Color(0xFFFFDC80)
    val Ink = Color(0xFF194773)
}

val HeroChipDay = Color(0xFFD8EAF9)

object MusicCardColors {
    val CardDay = Color(0xFFDCE9F8)
    val AccentDay = Color(0xFFFFC4B2)
    val AccentNight = Color(0xFFC6B9FF)
}

object CityCardColors {
    val SelectedNight = Color(0xFF2A527C)
    val SelectedLabelNight = Color(0xFFFFD46F)
}
