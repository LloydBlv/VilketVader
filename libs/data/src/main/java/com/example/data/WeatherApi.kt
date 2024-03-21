package com.example.data

import com.example.data.models.WeatherResponseDto
import com.example.data.models.toWeather
import com.example.domain.Weather
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query


enum class WeatherUnit(val value: String) {
    METRIC("metric"),
    IMPERIAL("imperial"),
    STANDARD("standard")
}

internal interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") weatherUnit: String,
        @Query("lang") language: String,
    ): WeatherResponseDto
}

class WeatherApiClientDefault(
    ktorfit: Ktorfit = getKtorfit()
) : WeatherApiClient {
    private val weatherApi = ktorfit.create<WeatherApi>()


    override suspend fun getWeather(cityName: String, language: String): Weather {
        return weatherApi.getWeather(
            cityName = cityName,
            weatherUnit = WeatherUnit.METRIC.value,
            language = language
        ).toWeather()
    }

    companion object {
        private fun getKtorfit(): Ktorfit {
            return NetModule.provideKtorfit(
                NetModule.provideHttpClient(
                    okHttpClient = NetModule.provideOkHttpClient(),
                    ktorJsonSettings = NetModule.provideJson()
                )
            )
        }
    }
}

interface WeatherApiClient {
    suspend fun getWeather(cityName: String, language: String): Weather
}