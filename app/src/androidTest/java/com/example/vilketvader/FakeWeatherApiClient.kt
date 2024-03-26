package com.example.vilketvader

import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.datasource.WeatherApiClient
import com.example.data.models.toWeather
import com.example.domain.Weather
import com.example.testing.TestData
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

class FakeWeatherApiClient @Inject constructor() : WeatherApiClient {
    override suspend fun getWeather(cityName: String, language: String): Weather {
        delay(500.milliseconds)
        return TestData.getTestResponse(
            cityName,
            context = InstrumentationRegistry.getInstrumentation().targetContext,
        ).toWeather()
    }
}
