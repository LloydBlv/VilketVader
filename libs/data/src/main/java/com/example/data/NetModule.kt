package com.example.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor(AuthInterceptor())
            .build()
        return okHttpClient
    }


    @Provides
    @Singleton
    fun provideKtorfit(
        httpClient: HttpClient
    ): Ktorfit {
        return Ktorfit
            .Builder()
            .httpClient(httpClient)
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpClient: OkHttpClient,
        ktorJsonSettings: Json,
    ): HttpClient {
        val httpClient = HttpClient(OkHttp) {
            engine {
                config {
                    preconfigured = okHttpClient
                    addInterceptor(AuthInterceptor())
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = if (true) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                        },
                    )
                }
            }
            install(ContentNegotiation) {
                json(ktorJsonSettings)
            }
        }
        return httpClient
    }


    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
//            encodeDefaults = true
            isLenient = true
            explicitNulls = false
//            allowSpecialFloatingPointValues = true
//            allowStructuredMapKeys = true
            prettyPrint = true
//            useArrayPolymorphism = false
            ignoreUnknownKeys = true
        }
    }
}