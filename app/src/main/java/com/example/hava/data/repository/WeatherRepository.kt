package com.example.hava.data.repository

import com.example.hava.data.remote.WeatherApi
import com.example.hava.data.remote.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse
}

class DefaultWeatherRepository(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse =
        weatherApi.getWeather(latitude = latitude, longitude = longitude)
}
