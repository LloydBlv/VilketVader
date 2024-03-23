package com.example.data

import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.local.LocalDataSource
import com.example.data.datasource.local.WeatherDao
import com.example.domain.Location
import com.example.domain.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.Validator
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

data class WeatherLoadParams(
    val location: Location,
    val language: String,
)

class WeatherStore @Inject constructor(
    apiClient: WeatherApiClient,
    dao: WeatherDao,
    localDataSource: LocalDataSource
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
            dao.deleteWeather(params.location.id)
        },
        deleteAll = dao::deleteAll
    )
        .usingDispatchers(
            readDispatcher = Dispatchers.IO.limitedParallelism(1),
            writeDispatcher = Dispatchers.IO.limitedParallelism(4)
        )
)
    .validator(
        Validator.by { result ->
            val now = Clock.System.now()
            val weatherTimeStamp = Instant.fromEpochSeconds(result.timestamp)
            Timber.d("now=%s, weatherTimeStamp=%s", now, weatherTimeStamp)
            val isUnderOneHours = now - weatherTimeStamp < 30.minutes
            Timber.d("isCacheValid=%s", isUnderOneHours)
            isUnderOneHours
        },
    )
    .build()