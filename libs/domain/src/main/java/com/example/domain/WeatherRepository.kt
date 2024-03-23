package com.example.domain

import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getWeather(
        location: Location,
        language: String,
        forceFresh: Boolean = false,
    ): Weather

    fun observeWeather(
        location: Location,
        language: String,
    ): Flow<Weather?>
}