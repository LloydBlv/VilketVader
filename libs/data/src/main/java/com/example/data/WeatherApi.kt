package com.example.data

import com.example.data.models.WeatherResponseDto
import com.example.data.models.toWeather
import com.example.domain.Weather
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
    ): WeatherResponseDto
}

class WeatherApiClientDefault(
    ktorfit: Ktorfit = getKtorfit()
) : WeatherApiClient {
    private val weatherApi = ktorfit.create<WeatherApi>()


    override suspend fun getWeather(cityName: String): Weather {
        return weatherApi.getWeather(cityName).toWeather()
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
    suspend fun getWeather(cityName: String): Weather
}