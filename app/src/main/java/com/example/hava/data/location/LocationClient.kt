package com.example.hava.data.location

import android.location.Location

/** ViewModel somut sınıfa değil bu arayüze bağlı, böylece test sırasında sahte konum verilebilir. */
interface LocationClient {
    suspend fun getCurrentLocation(): Location?
    suspend fun getCityName(latitude: Double, longitude: Double): String?
}
