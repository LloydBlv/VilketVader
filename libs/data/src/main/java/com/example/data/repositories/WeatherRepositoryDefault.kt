package com.example.data.repositories

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
//        if (localDataSource.getWeather(location) != null) {
//            return localDataSource.getWeather(location)!!
//        }
        return client.getWeather(location.name, language)
    }
}