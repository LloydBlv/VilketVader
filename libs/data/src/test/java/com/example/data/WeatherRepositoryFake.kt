package com.example.data

import com.example.data.models.WeatherResponseDto
import com.example.domain.Condition
import com.example.domain.Coordination
import com.example.domain.Location
import com.example.domain.Temperature
import com.example.domain.Weather
import com.example.domain.WeatherRepository
import com.example.domain.Wind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryFake : WeatherRepository {
    override fun getWeather(location: String): Flow<Weather> {
        return flow {
            emit(TestData.getTestResponse())
        }.map {
            it.toWeather()
        }
    }
}

private fun WeatherResponseDto.toWeather(): Weather {
    return Weather(
        location = Location(
            id = id ?: 0,
            name = name.orEmpty(),
            coordination = Coordination(
                latitude = coord?.lat ?: 0.0f,
                longitude = coord?.lon ?: 0.0f
            ),
            country = sys?.country.orEmpty(),
            timezone = timezone ?: 0,
        ),
        conditions = weather?.map {
            Condition(
                name = it?.main.orEmpty(),
                description = it?.description.orEmpty()
            )
        } ?: emptyList(),
        temperature = Temperature(
            current = main?.temp ?: 0.0f,
            feelsLike = main?.feelsLike ?: 0.0f,
            min = main?.tempMin ?: 0.0f,
            max = main?.tempMax ?: 0.0f,
        ),
        pressure = main?.pressure ?: 0,
        humidity = main?.humidity ?: 0,
        visibility = visibility ?: 0L,
        clouds = clouds?.all ?: 0,
        sunriseTimeMillis = sys?.sunriseTimeMillis ?: 0L,
        sunsetTimeMillis = sys?.sunsetTimeMillis ?: 0L,
        wind = Wind(
            speed = wind?.speed ?: 0.0f,
            degree = wind?.deg ?: 0
        ),
        timestamp = dt?.toLong() ?: 0L
    )

}
