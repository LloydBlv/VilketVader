package com.example.data.di

import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.WeatherApiClientDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WeatherApiModule {
    @Binds
    fun bindsWeatherApi(default: WeatherApiClientDefault): WeatherApiClient
}
