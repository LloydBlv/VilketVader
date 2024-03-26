package com.example.testing

import com.example.data.datasource.WeatherApiClient
import com.example.data.models.toWeather
import com.example.domain.Weather

class WeatherClientFake : WeatherApiClient {
    var exception: Throwable? = null

    override suspend fun getWeather(cityName: String, language: String): Weather {
        if (exception != null) {
            throw exception!!
        }
        val location = when (cityName.lowercase()) {
            "stockholm" -> TestData.STOCKHOLM
            else -> TestData.ZURICH
        }
        return TestData.getTestResponse(location.name).toWeather()
    }
}
