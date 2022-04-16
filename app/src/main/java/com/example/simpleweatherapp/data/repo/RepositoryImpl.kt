package com.example.simpleweatherapp.data.repo

import androidx.lifecycle.LiveData
import com.example.simpleweatherapp.data.local.LocalDataSource
import com.example.simpleweatherapp.data.remote.RemoteDataSource
import com.example.simpleweatherapp.data.local.LocalWeather
import com.example.simpleweatherapp.data.local.SearchItem
import com.example.simpleweatherapp.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    Repository {
    override suspend fun getCityDetails(cityName: String) =
        remoteDataSource.getCityDetails(cityName)

    override suspend fun insertWeather(localWeather: LocalWeather) {
        localDataSource.insertWeather(localWeather)
    }

    override suspend fun getWeatherHistory(cityName: String): List<LocalWeather> {
        return localDataSource.getList(cityName)
    }

    override suspend fun saveSearch(cityName: String) {
        localDataSource.saveSearch(cityName)
    }

    override fun getSearchList(): LiveData<List<SearchItem>> {
        return localDataSource.getSearchList()
    }

    override suspend fun check(cityName: String): Boolean {
        return localDataSource.check(cityName)
    }

}