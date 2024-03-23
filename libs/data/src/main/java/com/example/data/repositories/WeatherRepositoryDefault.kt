package com.example.data.repositories

import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.local.LocalDataSource
import com.example.domain.Location
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject


class WeatherRepositoryDefault @Inject constructor(
    private val client: WeatherApiClient,
    private val localDataSource: LocalDataSource
) : WeatherRepository {
    override suspend fun getWeather(
        location: Location, language: String,
        forceFresh: Boolean
    ): Weather {
        Timber.d("location=%s, language=%s, forceFresh=%s", location, language, forceFresh)
        if (!forceFresh) {
            val cachedWeather = localDataSource.getWeather(location.id)
            Timber.w("cachedWeather=%s", cachedWeather)
            if (cachedWeather != null) {
                return cachedWeather
            }
        }
        val weather = client.getWeather(location.name.lowercase(), language)
        localDataSource.updateWeather(weather)
        return weather
    }

    override fun observeWeather(location: Location, language: String): Flow<Weather?> {
        return localDataSource.observeWeather(location.id)
    }
}