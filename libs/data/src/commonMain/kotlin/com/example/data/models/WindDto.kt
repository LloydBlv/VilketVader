package com.example.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindDto(
    @SerialName("deg") val deg: Int?,
    @SerialName("speed") val speed: Float?,
)
