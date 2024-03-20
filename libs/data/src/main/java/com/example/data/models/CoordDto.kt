package com.example.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordDto(
    @SerialName("lat") val lat: Float?,
    @SerialName("lon") val lon: Float?
)