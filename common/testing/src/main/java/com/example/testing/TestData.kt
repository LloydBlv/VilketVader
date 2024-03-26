package com.example.testing

import android.content.Context
import com.example.data.models.WeatherResponseDto
import com.example.domain.Coordination
import com.example.domain.Location
import java.io.BufferedReader
import java.io.File
import kotlinx.serialization.json.Json
import okio.BufferedSource
import okio.buffer
import okio.source

object TestData {
    private const val ASSET_BASE_PATH = "../app/src/debug/assets/"
    private const val RESOURCES_FOLDER_PATH = "../../libs/data/src/test/resources"

    fun getTestResponse(
        location: Location = STOCKHOLM,
        context: Context? = null,
        json: Json = Json { ignoreUnknownKeys = true },
    ): WeatherResponseDto {
        val rawTestResponse = try {
            getRawJsonFromResources(location)
        } catch (e: Exception) {
            val loadAssets = context?.loadAssets(getAssetsFilePath(location))
            loadAssets?.use { it.readUtf8() }.orEmpty()
        }

        val weatherDto = json.decodeFromString<WeatherResponseDto>(rawTestResponse)
        return weatherDto
    }

    private fun getResourceFilePath(location: Location): String {
        return if (location.name == STOCKHOLM.name) {
            "$RESOURCES_FOLDER_PATH/stockholm-weather-response.json"
        } else {
            "$RESOURCES_FOLDER_PATH/zurich-rainy-response.json"
        }
    }

    private fun getAssetsFilePath(location: Location): String {
        return if (location.name == STOCKHOLM.name) {
            "stockholm-weather-response.json"
        } else {
            "zurich-rainy-response.json"
        }
    }

    fun getRawJsonFromResources(location: Location = STOCKHOLM): String {
        val file = File(getResourceFilePath(location))
        val rawJson = file.bufferedReader().use(BufferedReader::readText)
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

    fun Context.loadAssets(fileName: String): BufferedSource {
        return try {
            File("$ASSET_BASE_PATH$fileName").source().buffer()
        } catch (e: Exception) {
            assets.open(fileName).source().buffer()
        }
    }
}
