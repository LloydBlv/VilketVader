package com.example.data

import com.example.data.models.WeatherResponseDto
import kotlinx.serialization.json.Json
import java.io.File

internal object TestData {
    fun getTestResponse(json: Json = Json { ignoreUnknownKeys = true }): WeatherResponseDto {
        val file = File("src/test/resources/stockholm-weather-response.json")
        val rawJson =
            file.bufferedReader().use {
                it.readText()
            }
        val weatherDto = json.decodeFromString<WeatherResponseDto>(rawJson)
        return weatherDto
    }
}