package com.example.testing

import com.example.data.models.toWeather
import com.example.domain.Location
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryFake() : WeatherRepository {
    var exception: Throwable? = null
    override suspend fun getWeather(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ): Weather {
        if (exception != null) {
            throw exception!!
        }
        return TestData.getTestResponse(location).toWeather()
    }

    override fun observeWeather(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ): Flow<Weather?> {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(location: Location, language: String) {
        TODO("Not yet implemented")
    }
}
