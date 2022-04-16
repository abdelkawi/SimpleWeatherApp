package com.example.simpleweatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalWeather::class,SearchItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun searchDao():SearchHistoryDao
}