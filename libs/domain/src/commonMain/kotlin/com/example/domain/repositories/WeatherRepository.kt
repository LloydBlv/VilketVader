package com.example.domain.repositories

import com.example.domain.models.Weather
import com.example.domain.models.Location
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
        forceFresh: Boolean,
    ): Flow<Weather?>

    suspend fun refresh(location: Location, language: String)
}
