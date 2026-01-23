package com.example.data.datasource

import com.example.data.models.WeatherResponseDto
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

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
