package com.example.testing

import com.example.data.WeatherStore
import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.local.LocalDataSource
import com.example.data.datasource.local.WeatherDao
import kotlinx.coroutines.CoroutineDispatcher

fun createFakeWeatherStore(
    dao: WeatherDao = FakeWeatherDao(),
    client: WeatherApiClient = WeatherClientFake(),
    localDataSource: LocalDataSource = FakeLocalDataSource(),
    testDispatcher: CoroutineDispatcher,
): WeatherStore {
    return WeatherStore(
        apiClient = client,
        dao = dao,
        localDataSource = localDataSource,
        readDispatcher = testDispatcher,
        writeDispatcher = testDispatcher,
    )
}
