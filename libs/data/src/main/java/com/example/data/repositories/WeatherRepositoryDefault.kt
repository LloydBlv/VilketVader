package com.example.data.repositories

import com.example.data.WeatherLoadParams
import com.example.data.WeatherStore
import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.local.LocalDataSource
import com.example.data.filterForResult
import com.example.domain.Location
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import timber.log.Timber
import javax.inject.Inject


class WeatherRepositoryDefault @Inject constructor(
    private val client: WeatherApiClient,
    private val localDataSource: LocalDataSource,
    private val weatherStore: WeatherStore
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

    override fun observeWeather(
        location: Location,
        language: String,
        forceFresh: Boolean
    ): Flow<Weather?> {
        return weatherStore
            .stream(request = createRequest(location, language, forceFresh))
            .filterForResult()
            .map(StoreReadResponse<Weather>::requireData)
    }

    override suspend fun refresh(location: Location, language: String) {
        Timber.d("going to request fresh weather for location=%s, language=%s", location, language)
        weatherStore.fresh(key = WeatherLoadParams(location, language))
    }

    private fun createRequest(
        location: Location,
        language: String,
        forceFresh: Boolean
    ) = StoreReadRequest.cached(
        key = WeatherLoadParams(location, language),
        refresh = forceFresh
    )
}