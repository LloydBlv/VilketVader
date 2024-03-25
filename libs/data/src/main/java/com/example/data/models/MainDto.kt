package com.example.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainDto(
    @SerialName("feels_like") val feelsLike: Float?,
    @SerialName("humidity") val humidity: Int?,
    @SerialName("pressure") val pressure: Int?,
    @SerialName("temp") val temp: Float?,
    @SerialName("temp_max") val tempMax: Float?,
    @SerialName("temp_min") val tempMin: Float?,
)
