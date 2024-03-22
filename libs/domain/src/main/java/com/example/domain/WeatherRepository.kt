package com.example.domain


interface WeatherRepository {
    suspend fun getWeather(
        location: Location,
        language: String
    ): Weather
}