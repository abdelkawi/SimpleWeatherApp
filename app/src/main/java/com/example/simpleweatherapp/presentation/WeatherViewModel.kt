package com.example.simpleweatherapp.presentation

import androidx.lifecycle.ViewModel
import com.example.simpleweatherapp.data.local.LocalWeather
import com.example.simpleweatherapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    suspend fun getCityDetails(cityName: String) =
        repository.getCityDetails(cityName)

    suspend fun insertWeather(localWeather: LocalWeather) =
        repository.insertWeather(localWeather)

    suspend fun getWeatherHistory(name: String) = repository.getWeatherHistory(name)

    suspend fun saveSearch(cityName: String) = repository.saveSearch(cityName)

     fun getSearchList() = repository.getSearchList()

    suspend fun checkCityName(cityName: String) = repository.check(cityName)


}