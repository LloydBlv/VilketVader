package com.example.testing

import com.example.data.WeatherStore
import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.local.LocalDataSource
import com.example.data.datasource.local.WeatherDao

fun createFakeWeatherStore(
    dao: WeatherDao = FakeWeatherDao(),
    client: WeatherApiClient = WeatherClientFake(),
    localDataSource: LocalDataSource = FakeLocalDataSource(),
): WeatherStore {
    return WeatherStore(
        apiClient = client,
        dao = dao,
        localDataSource = localDataSource,
    )
}
