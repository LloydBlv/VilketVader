package com.example.data.di

import com.example.data.WeatherStore
import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.LocationDataSource
import com.example.data.datasource.WeatherApiClient
import com.example.data.repositories.LocationRepositoryDefault
import com.example.data.repositories.WeatherRepositoryDefault
import com.example.domain.repositories.LocationRepository
import com.example.domain.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single<WeatherRepository> {
        WeatherRepositoryDefault(
            client = get(),
            localDataSource = get(),
            weatherStore = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }

    single<LocationRepository> {
        LocationRepositoryDefault(
            locationDataSource = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }

    single {
        WeatherStore(
            apiClient = get(),
            localDataSource = get(),
            readDispatcher = Dispatchers.IO,
            writeDispatcher = Dispatchers.IO,
        )
    }
}
