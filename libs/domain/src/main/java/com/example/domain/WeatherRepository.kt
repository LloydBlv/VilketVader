package com.example.domain

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(location: String): Flow<Weather>
}