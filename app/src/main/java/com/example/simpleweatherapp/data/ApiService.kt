package com.example.simpleweatherapp.data

import com.example.simpleweatherapp.data.response.CityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather")
    suspend fun getCityDetails(
        @Query("appid") id: String = "f5cb0b965ea1564c50c6f1b74534d823",
        @Query("q") cityName: String
    ): Response<CityResponse>
}