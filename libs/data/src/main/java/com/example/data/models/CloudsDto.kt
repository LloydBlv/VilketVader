package com.example.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CloudsDto(
    @SerialName("all") val all: Int?
)