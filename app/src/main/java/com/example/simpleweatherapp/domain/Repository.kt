package com.example.simpleweatherapp.domain

import androidx.lifecycle.LiveData
import com.example.simpleweatherapp.data.response.CityResponse
import com.example.simpleweatherapp.data.Result
import com.example.simpleweatherapp.data.local.LocalWeather
import com.example.simpleweatherapp.data.local.SearchItem

interface Repository {
    suspend fun getCityDetails(cityName: String): Result<CityResponse>
    suspend fun insertWeather(localWeather: LocalWeather)
    suspend fun getWeatherHistory(cityName: String): List<LocalWeather>
    suspend fun saveSearch(cityName: String)
    fun getSearchList(): LiveData<List<SearchItem>>
    suspend fun check(cityName: String):Boolean
}