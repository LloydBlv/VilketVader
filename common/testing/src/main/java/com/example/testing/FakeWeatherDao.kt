package com.example.testing

import com.example.data.datasource.local.WeatherAndLocation
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FakeWeatherDao: WeatherDao {
    override suspend fun getWeather(locationId: Int): WeatherAndLocation? {
        return null
    }

    override suspend fun getAllWeathers(): List<WeatherAndLocation> {
        return emptyList()
    }

    override suspend fun getOnlyAllWeathers(): List<WeatherEntity> {
        return emptyList()
    }

    override fun observeWeather(id: Int): Flow<WeatherAndLocation?> {
        return emptyFlow()
    }

    override suspend fun insertWeather(weather: WeatherEntity) {
        //NO OP
    }

    override suspend fun deleteWeather(locationId: Int) {
        //NO OP
    }

    override suspend fun deleteAll() {
        //NO OP
    }
}