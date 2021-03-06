package com.example.simpleweatherapp.data.remote

import com.example.simpleweatherapp.data.remote.response.CityResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getCityDetails(cityName: String): Result<CityResponse> =
        withContext(Dispatchers.IO) {
            safeApiCall {
                apiService.getCityDetails(cityName = cityName)
            }
        }
}