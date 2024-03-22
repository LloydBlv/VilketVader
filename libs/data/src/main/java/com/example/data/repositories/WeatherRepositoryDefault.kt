package com.example.data.repositories

import com.example.data.WeatherApiClient
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import javax.inject.Inject


class WeatherRepositoryDefault @Inject constructor(
    private val client: WeatherApiClient
) : WeatherRepository {
    override suspend fun getWeather(location: String, language: String): Weather {
        return client.getWeather(location, language)
    }
}