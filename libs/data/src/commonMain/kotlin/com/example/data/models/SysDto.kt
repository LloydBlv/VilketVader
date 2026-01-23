package com.example.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SysDto(
    @SerialName("country") val country: String?,
    @SerialName("id") val id: Int?,
    @SerialName("sunrise") val sunriseTimeMillis: Long?,
    @SerialName("sunset") val sunsetTimeMillis: Long?,
    @SerialName("type") val type: Int?,
)
