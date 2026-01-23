package com.example.data.datasource

import com.example.domain.models.Weather

interface WeatherApiClient {
    suspend fun getWeather(cityName: String, language: String): Weather
}
