package com.example.domain


interface WeatherRepository {
    suspend fun getWeather(
        location: String,
        language: String
    ): Weather
}