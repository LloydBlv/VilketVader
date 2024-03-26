package com.example.testing

import android.content.Context
import com.example.data.models.toWeather
import com.example.domain.Location
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryFake(val context: Context? = null) : WeatherRepository {
    var exception: Throwable? = null
    override suspend fun getWeather(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ): Weather {
        if (exception != null) {
            throw exception!!
        }
        delay(1.seconds)
        return TestData.getTestResponse(location, context).toWeather()
    }

    override fun observeWeather(
        location: Location,
        language: String,
        forceFresh: Boolean,
    ): Flow<Weather?> {
        return flow {
            emit(getWeather(location, language, forceFresh))
        }
    }

    override suspend fun refresh(location: Location, language: String) {
    }
}
