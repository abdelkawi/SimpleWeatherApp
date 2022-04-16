package com.example.simpleweatherapp.data.local


import javax.inject.Inject


class LocalDataSource @Inject constructor() {
    @Inject
    lateinit var weatherDao: WeatherDao

    @Inject
    lateinit var searchHistoryDao: SearchHistoryDao
    suspend fun getList(name: String): List<LocalWeather> {
        return weatherDao.findByName(name)
    }

    suspend fun insertWeather(localWeather: LocalWeather) = weatherDao.insert(localWeather)

    suspend fun saveSearch(cityName: String) =
        searchHistoryDao.insert(SearchItem(cityName = cityName))

    fun getSearchList() = searchHistoryDao.getSearchList()
    suspend fun check(cityName: String) = searchHistoryDao.exists(cityName)
}