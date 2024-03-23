package com.example.testing

import com.example.data.models.toWeather
import com.example.domain.Location
import com.example.domain.Weather
import com.example.domain.WeatherRepository

class WeatherRepositoryFake(
) : WeatherRepository {
    var exception: Throwable? = null
    override suspend fun getWeather(location: Location, language: String): Weather {
        if (exception != null) {
            throw exception!!
        }
        return TestData.getTestResponse(location).toWeather()
    }
}

