package com.example.data.repositories

import android.util.Log
import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.local.LocalDataSource
import com.example.domain.Location
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import javax.inject.Inject


class WeatherRepositoryDefault @Inject constructor(
    private val client: WeatherApiClient,
    private val localDataSource: LocalDataSource
) : WeatherRepository {
    override suspend fun getWeather(location: Location, language: String): Weather {
        val cachedWeather = localDataSource.getWeather(location.id)
        Log.e("cachedWeather", cachedWeather.toString())
        if (cachedWeather != null) {
            return cachedWeather
        }
        val weather = client.getWeather(location.name.lowercase(), language)
        localDataSource.updateWeather(weather)
        return weather
    }
}