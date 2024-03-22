package com.example.data.datasource.local

import com.example.domain.Weather
import javax.inject.Inject


interface LocalDataSource {
    suspend fun getWeather(id: Int): Weather?

    suspend fun updateWeather(weather: Weather)
}

class LocalDataSourceDefault @Inject constructor(
    private val weatherDao: WeatherDao
) : LocalDataSource {
    override suspend fun getWeather(id: Int) = weatherDao.getWeather(id)?.toDomain()
    override suspend fun updateWeather(weather: Weather) {
        weatherDao.insertWeather(weather.toEntity())
    }
}