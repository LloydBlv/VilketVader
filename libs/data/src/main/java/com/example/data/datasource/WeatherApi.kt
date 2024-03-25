package com.example.data.datasource

import com.example.data.models.WeatherResponseDto
import com.example.data.models.toWeather
import com.example.domain.Weather
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

enum class WeatherUnit(val value: String) {
    METRIC("metric"),
    IMPERIAL("imperial"),
    STANDARD("standard"),
}

internal interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") weatherUnit: String,
        @Query("lang") language: String,
    ): WeatherResponseDto
}

class WeatherApiClientDefault @Inject constructor(
    ktorfit: dagger.Lazy<Ktorfit>,
) : WeatherApiClient {
    private val weatherApi = ktorfit.get().create<WeatherApi>()
    override suspend fun getWeather(cityName: String, language: String): Weather {
        delay(1.seconds)
        return weatherApi.getWeather(
            cityName = cityName,
            weatherUnit = WeatherUnit.METRIC.value,
            language = language,
        ).toWeather()
    }
}

interface WeatherApiClient {
    suspend fun getWeather(cityName: String, language: String): Weather
}
