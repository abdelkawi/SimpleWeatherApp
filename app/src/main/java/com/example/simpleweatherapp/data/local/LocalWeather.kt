package com.example.simpleweatherapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class LocalWeather(
    @PrimaryKey(autoGenerate = true) val id:Int? =null,
    @ColumnInfo(name = "city_name") val name:String,
    @ColumnInfo(name = "desc") val desc:String,
    @ColumnInfo(name = "icon") val icon:String,
    @ColumnInfo(name = "time") val time:String,
    @ColumnInfo(name = "temp") val temp:Double,
    @ColumnInfo(name = "speed") val speed:Double,
    @ColumnInfo(name = "humidity") val humidity:Int
)