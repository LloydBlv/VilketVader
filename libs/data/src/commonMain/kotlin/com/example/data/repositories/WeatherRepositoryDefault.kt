package com.example.data.repositories

import co.touchlab.kermit.Logger
import com.example.data.WeatherLoadParams
import com.example.data.WeatherStore
import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.WeatherApiClient
import com.example.data.filterForResult
import com.example.domain.models.Location
import com.example.domain.models.Weather
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.impl.extensions.fresh

class WeatherRepositoryDefault(
    private val client: WeatherApiClient,
    private val localDataSource: LocalDataSource,
    private val weatherStore: WeatherStore,
    private val ioDispatcher: CoroutineDispatcher,
) : WeatherRepository {
    override suspend fun getWeather(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ): Weather = withContext(ioDispatcher) {
        Logger.d { "location=$location, language=$language, forceFresh=$forceFresh" }
        if (!forceFresh) {
            val cachedWeather = localDataSource.getWeather(location.id)
            Logger.w { "cachedWeather=$cachedWeather" }
            if (cachedWeather != null) {
                return@withContext cachedWeather
            }
        }
        val weather = client.getWeather(location.name.lowercase(), language)
        localDataSource.updateWeather(weather)
        weather
    }

    override fun observeWeather(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ): Flow<Weather?> {
        return weatherStore
            .stream(request = createRequest(location, language, forceFresh))
            .filterForResult()
            .map(StoreReadResponse<Weather>::requireData)
            .flowOn(ioDispatcher)
    }

    override suspend fun refresh(location: Location, language: String) {
        Logger.d { "going to request fresh weather for location=$location, language=$language" }
        weatherStore.fresh(key = WeatherLoadParams(location, language))
    }

    private fun createRequest(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ) = StoreReadRequest.cached(
        key = WeatherLoadParams(location, language),
        refresh = forceFresh,
    )
}
