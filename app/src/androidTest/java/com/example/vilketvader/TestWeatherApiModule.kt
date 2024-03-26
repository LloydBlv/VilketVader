package com.example.vilketvader

import com.example.data.datasource.WeatherApiClient
import com.example.data.di.WeatherApiModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [WeatherApiModule::class],
)
interface TestWeatherApiModule {
    @Binds
    fun bindsWeatherApi(fake: FakeWeatherApiClient): WeatherApiClient
}
