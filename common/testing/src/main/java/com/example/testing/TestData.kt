package com.example.testing

import com.example.data.models.WeatherResponseDto
import com.example.domain.Coordination
import com.example.domain.Location
import kotlinx.serialization.json.Json
import java.io.File

object TestData {
    fun getTestResponse(json: Json = Json { ignoreUnknownKeys = true }): WeatherResponseDto {
        val weatherDto = json.decodeFromString<WeatherResponseDto>(getRawTestResponse())
        return weatherDto
    }

    fun getRawTestResponse(): String {
        val file = File("../../libs/data/src/test/resources/stockholm-weather-response.json")
        val rawJson =
            file.bufferedReader().use {
                it.readText()
            }
        return rawJson
    }

    val STOCKHOLM: Location = Location(
        id = 2673730,
        name = "stockholm",
        coordination = Coordination(59.3326f, 18.0649f),
        timezone = 3600,
        country = "SE",
        isSelected = false
    )

    val ZURICH: Location = Location(
        id = 2657896,
        name = "zurich",
        coordination = Coordination(47.3667f, 8.5500f),
        timezone = 3600,
        country = "CH",
        isSelected = false
    )
}