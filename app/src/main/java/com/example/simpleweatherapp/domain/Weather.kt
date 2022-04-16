package com.example.simpleweatherapp.domain

import com.example.simpleweatherapp.data.local.LocalWeather
import com.example.simpleweatherapp.data.remote.response.CityResponse
import com.example.simpleweatherapp.data.remote.response.Main
import com.example.simpleweatherapp.data.remote.response.WeatherItem
import com.example.simpleweatherapp.data.remote.response.Wind

fun CityResponse.toLocalWeather(): LocalWeather {
    return LocalWeather(
        name = this.name ?: "",
        desc = this.weather?.get(0)?.description ?: "",
        time = System.currentTimeMillis().toString(),
        humidity = this.main?.humidity ?: 0,
        icon = "http://openweathermap.org/img/w/${this.weather?.get(0)?.icon}.png",
        speed = this.wind?.speed ?: 0.0,
        temp = this.main?.temp ?: 0.0
    )
}

fun LocalWeather.toCityResponse():CityResponse{
    return CityResponse(
        name = this.name,
        weather = listOf(WeatherItem(icon= this.icon, description = this.desc)),
        main = Main(temp = this.temp, humidity = this.humidity),
        wind = Wind(speed = this.speed)
    )
}