package com.example.simpleweatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM localweather WHERE city_name LIKE :cityName")
    suspend fun findByName(cityName: String): List<LocalWeather>

    @Insert(onConflict = REPLACE)
    suspend fun insert(localWeather: LocalWeather)

    @Delete
    suspend fun delete(weather: LocalWeather)
}