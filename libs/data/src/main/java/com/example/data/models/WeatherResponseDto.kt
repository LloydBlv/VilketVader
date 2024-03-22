package com.example.data.models


import com.example.domain.Condition
import com.example.domain.Coordination
import com.example.domain.Location
import com.example.domain.Temperature
import com.example.domain.Weather
import com.example.domain.Wind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    @SerialName("clouds") val clouds: CloudsDto?,
    @SerialName("coord") val coord: CoordDto?,
    @SerialName("dt") val dt: Int?,
    @SerialName("id") val id: Int?,
    @SerialName("main") val main: MainDto?,
    @SerialName("name") val name: String?,
    @SerialName("sys") val sys: SysDto?,
    @SerialName("timezone") val timezone: Int?,
    @SerialName("visibility") val visibility: Long?,
    @SerialName("weather") val weather: List<WeatherDto?>?,
    @SerialName("wind") val wind: WindDto?
)

fun WeatherResponseDto.toWeather(): Weather {
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
            isSelected = false
        ),
        conditions = weather?.map {
            Condition(
                name = it?.main.orEmpty(),
                description = it?.description.orEmpty(),
                type = Condition.Type.from(it?.id ?: 0)
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
        timestamp = dt?.toLong() ?: 0L,
        icon = weather?.firstOrNull()?.icon.orEmpty()
    )

}
