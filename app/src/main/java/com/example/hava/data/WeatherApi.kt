package com.example.hava.data

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Mevcut hava durumu + saatlik tahmin + 7 günlük tahmin tek API yanıtında.
data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: HourlyForecastData,
    val daily: DailyForecastData
)

data class CurrentWeather(
    val time: String,
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("apparent_temperature") val feelsLike: Double,
    @SerializedName("relative_humidity_2m") val humidity: Int,
    @SerializedName("wind_speed_10m") val windSpeed: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("is_day") val isDay: Int
)

data class HourlyForecastData(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperatures: List<Double>,
    @SerializedName("weather_code") val weatherCodes: List<Int>,
    @SerializedName("is_day") val dayFlags: List<Int>
)

data class DailyForecastData(
    val time: List<String>,
    @SerializedName("weather_code") val weatherCodes: List<Int>,
    @SerializedName("temperature_2m_max") val maxTemperatures: List<Double>,
    @SerializedName("temperature_2m_min") val minTemperatures: List<Double>
)

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m,weather_code,is_day",
        @Query("hourly") hourly: String = "temperature_2m,weather_code,is_day",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("forecast_days") forecastDays: Int = 7,
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}

object WeatherApiClient {
    val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)
}
