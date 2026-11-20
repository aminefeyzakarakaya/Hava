package com.example.hava.domain

/**
 * Open-Meteo'nun WMO hava kodlarını uygulamanın kendi diline çevirir.
 * Hava kodu karşılaştırmaları (kod in 51..67 gibi) artık sadece burada yapılır.
 */
enum class WeatherCondition {
    CLEAR,
    MOSTLY_CLEAR,
    PARTLY_CLOUDY,
    CLOUDY,
    FOG,
    DRIZZLE,
    RAIN,
    SNOW,
    THUNDERSTORM,
    UNKNOWN;

    /** Çisenti, yağmur ve gök gürültülü fırtına: hepsi ıslak hava sayılır. */
    val isRainy: Boolean
        get() = this == DRIZZLE || this == RAIN || this == THUNDERSTORM

    val isSnowy: Boolean
        get() = this == SNOW

    /** Kapalı gökyüzü: bulutlu ya da sisli. */
    val isOvercast: Boolean
        get() = this == CLOUDY || this == FOG

    /** İkonda güneş (ya da gece ay) görünsün mü? */
    val showsSun: Boolean
        get() = this == CLEAR || this == MOSTLY_CLEAR || this == PARTLY_CLOUDY

    /** İkonda bulut görünsün mü? Açık havalar dışında hepsinde var. */
    val showsCloud: Boolean
        get() = this != CLEAR && this != MOSTLY_CLEAR

    companion object {
        fun fromCode(code: Int): WeatherCondition = when (code) {
            0 -> CLEAR
            1 -> MOSTLY_CLEAR
            2 -> PARTLY_CLOUDY
            3 -> CLOUDY
            45, 48 -> FOG
            in 51..57 -> DRIZZLE
            in 61..67, in 80..82 -> RAIN
            in 71..77, 85, 86 -> SNOW
            in 95..99 -> THUNDERSTORM
            else -> UNKNOWN
        }
    }
}