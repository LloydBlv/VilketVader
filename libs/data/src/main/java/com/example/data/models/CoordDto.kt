package com.example.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CoordDto(
    @SerialName("lat") val lat: Float?,
    @SerialName("lon") val lon: Float?
)