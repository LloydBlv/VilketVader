package com.example.data.repositories

import com.example.data.WeatherApiClient
import com.example.domain.Weather
import com.example.domain.WeatherRepository

class WeatherRepositoryDefault(private val client: WeatherApiClient) : WeatherRepository {
    override suspend fun getWeather(location: String): Weather {
        return client.getWeather(location)
    }
}