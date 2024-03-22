package com.example.data.datasource.local

import com.example.domain.Weather
import javax.inject.Inject
import javax.inject.Singleton


interface LocalDataSource {
    suspend fun getWeather(id: Int): Weather?

    suspend fun updateWeather(weather: Weather)
}

@Singleton
class LocalDataSourceDefault @Inject constructor(
    private val weatherDao: WeatherDao,
    private val locationDao: LocationDao
) : LocalDataSource {
    override suspend fun getWeather(id: Int) = weatherDao.getWeather(id)?.toDomain()
    override suspend fun updateWeather(weather: Weather) {
        locationDao.insertLocation(weather.location.toEntity())
        weatherDao.insertWeather(weather.toEntity())
    }
}