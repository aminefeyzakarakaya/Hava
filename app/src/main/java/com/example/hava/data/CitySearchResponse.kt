package com.example.hava.data

data class ApiCity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?
)

data class CitySearchResponse(
    val results: List<ApiCity>?
)