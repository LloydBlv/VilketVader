package com.example.testing

import com.example.data.datasource.local.LocalDataSource
import com.example.data.models.toWeather
import com.example.domain.Weather

class FakeLocalDataSource: LocalDataSource {
    var exception: Throwable? = null
    override suspend fun getWeather(id: Int): Weather? {
        if (exception != null) {
            throw exception!!
        }
        return TestData.getTestResponse().toWeather()
    }

    override suspend fun updateWeather(weather: Weather) {
        // do nothing
    }
}