package com.example.data

import co.touchlab.kermit.Logger
import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.WeatherApiClient
import com.example.domain.models.Location
import com.example.domain.models.Weather
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.Validator

data class WeatherLoadParams(
    val location: Location,
    val language: String,
)

class WeatherStore(
    apiClient: WeatherApiClient,
    localDataSource: LocalDataSource,
    readDispatcher: CoroutineDispatcher,
    writeDispatcher: CoroutineDispatcher,
) : Store<WeatherLoadParams, Weather> by storeBuilder(
    fetcher = Fetcher.of {
        apiClient.getWeather(it.location.name, it.language)
    },
    sourceOfTruth = SourceOfTruth.of(
        reader = { params ->
            localDataSource.observeWeather(params.location.id)
        },
        writer = { params: WeatherLoadParams, response: Weather ->
            localDataSource.updateWeather(weather = response)
        },
        delete = { params ->
            localDataSource.deleteWeather(params.location.id)
        },
        deleteAll = localDataSource::deleteAll,
    )
        .usingDispatchers(
            readDispatcher = readDispatcher,
            writeDispatcher = writeDispatcher,
        ),
)
    .validator(
        Validator.by { result ->
            val now = Clock.System.now()
            val weatherTimeStamp = Instant.fromEpochSeconds(result.timestamp)
            Logger.d { "now=$now, weatherTimeStamp=$weatherTimeStamp" }
            val isUnderOneHours = now - weatherTimeStamp < 30.minutes
            Logger.d { "isCacheValid=$isUnderOneHours" }
            isUnderOneHours
        },
    )
    .build()
