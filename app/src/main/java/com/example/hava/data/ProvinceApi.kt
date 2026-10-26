
package com.example.hava.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Province(
    val name: String,
    val coordinates: ProvinceCoordinates?
)

data class ProvinceCoordinates(
    val latitude: Double,
    val longitude: Double
)

data class ProvinceResponse(
    val data: List<Province>
)

interface ProvinceApi {
    @GET("v2/provinces")
    suspend fun getProvinces(
        @Query("limit") limit: Int = 100,
        @Query("fields") fields: String = "name,coordinates"
    ): ProvinceResponse
}

object ProvinceApiClient {
    val api: ProvinceApi = Retrofit.Builder()
        .baseUrl("https://api.turkiyeapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProvinceApi::class.java)
}
