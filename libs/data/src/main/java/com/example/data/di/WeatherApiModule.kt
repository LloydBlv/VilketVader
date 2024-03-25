package com.example.data.di

import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.WeatherApiClientDefault
import com.example.data.repositories.WeatherRepositoryDefault
import com.example.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WeatherApiModule {
    @Binds
    fun bindsWeatherApi(default: WeatherApiClientDefault): WeatherApiClient

    @Binds
    fun bindsWeatherRepository(default: WeatherRepositoryDefault): WeatherRepository
}
