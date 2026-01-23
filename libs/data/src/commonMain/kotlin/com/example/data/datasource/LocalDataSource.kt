package com.example.data.datasource

import com.example.domain.models.Weather
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getWeather(id: Int): Weather?
    fun observeWeather(id: Int): Flow<Weather?>
    suspend fun updateWeather(weather: Weather)
    suspend fun deleteWeather(locationId: Int)
    suspend fun deleteAll()
}
