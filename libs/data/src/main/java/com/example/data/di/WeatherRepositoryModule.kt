package com.example.data.di

import com.example.data.repositories.WeatherRepositoryDefault
import com.example.domain.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WeatherRepositoryModule {
    @Binds
    fun bindsWeatherRepository(default: WeatherRepositoryDefault): WeatherRepository
}
