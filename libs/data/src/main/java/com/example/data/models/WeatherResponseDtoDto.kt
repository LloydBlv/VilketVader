package com.example.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponseDto(
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