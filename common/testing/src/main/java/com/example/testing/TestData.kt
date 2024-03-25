package com.example.testing

import com.example.data.models.WeatherResponseDto
import com.example.domain.Coordination
import com.example.domain.Location
import java.io.File
import kotlinx.serialization.json.Json

object TestData {
    fun getTestResponse(
        location: Location = STOCKHOLM,
        json: Json = Json { ignoreUnknownKeys = true },
    ): WeatherResponseDto {
        val weatherDto = json.decodeFromString<WeatherResponseDto>(getRawTestResponse(location))
        return weatherDto
    }

    fun getRawTestResponse(location: Location = STOCKHOLM): String {
        val file = if (location.name == STOCKHOLM.name) {
            File("../../libs/data/src/test/resources/stockholm-weather-response.json")
        } else {
            File("../../libs/data/src/test/resources/zurich-rainy-response.json")
        }
        val rawJson =
            file.bufferedReader().use {
                it.readText()
            }
        return rawJson
    }

    val STOCKHOLM: Location = Location(
        id = 2673730,
        name = "Stockholm",
        coordination = Coordination(59.3326f, 18.0649f),
        timezone = 3600,
        country = "SE",
        isSelected = false,
    )

    val ZURICH: Location = Location(
        id = 2657896,
        name = "Zurich",
        coordination = Coordination(47.3667f, 8.55f),
        timezone = 3600,
        country = "CH",
        isSelected = false,
    )
}
