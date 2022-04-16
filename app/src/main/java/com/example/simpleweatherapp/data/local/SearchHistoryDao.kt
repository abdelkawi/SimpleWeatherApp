package com.example.simpleweatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityName: SearchItem)

    @Query("SELECT * FROM searchitem")
    fun getSearchList(): LiveData<List<SearchItem>>

    @Query("SELECT EXISTS (SELECT 1 FROM searchitem WHERE city_name like :cityName)")
    suspend fun exists(cityName: String): Boolean
}