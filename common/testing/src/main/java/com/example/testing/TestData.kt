package com.example.testing

import com.example.data.models.WeatherResponseDto
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

}