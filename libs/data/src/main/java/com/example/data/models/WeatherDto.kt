package com.example.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("description") val description: String?,
    @SerialName("icon") val icon: String?,
    @SerialName("id") val id: Int?,
    @SerialName("main") val main: String?
)