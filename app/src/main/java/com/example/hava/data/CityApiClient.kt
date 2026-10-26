
package com.example.hava.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CityApiClient {

    val api: CityApi = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CityApi::class.java)
}
