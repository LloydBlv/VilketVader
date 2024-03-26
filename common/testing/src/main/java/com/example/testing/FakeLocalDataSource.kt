package com.example.testing

import com.example.data.datasource.local.LocalDataSource
import com.example.domain.models.Weather
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource : LocalDataSource {
    var exception: Throwable? = null
    override suspend fun getWeather(id: Int): Weather? {
        if (exception != null) {
            throw exception!!
        }
        return null
    }

    override suspend fun updateWeather(weather: Weather) {
        // do nothing
    }

    override fun observeWeather(id: Int): Flow<Weather?> {
        TODO("Not yet implemented")
    }
}
