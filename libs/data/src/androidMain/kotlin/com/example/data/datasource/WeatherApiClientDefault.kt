package com.example.data.datasource

import com.example.data.models.toWeather
import com.example.domain.models.Weather
import de.jensklingenberg.ktorfit.Ktorfit
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

class WeatherApiClientDefault(
    ktorfitProvider: () -> Ktorfit,
) : WeatherApiClient {
    private val weatherApi by lazy { ktorfitProvider().create<WeatherApi>() }

    override suspend fun getWeather(cityName: String, language: String): Weather {
        delay(1.seconds)
        return weatherApi.getWeather(
            cityName = cityName,
            weatherUnit = WeatherUnit.METRIC.value,
            language = language,
        ).toWeather()
    }
}
