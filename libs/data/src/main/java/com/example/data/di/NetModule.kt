package com.example.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.data.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    fun provideKtorfit(
        httpClient: HttpClient,
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
        @ApplicationContext context: Context,
        ktorJsonSettings: Json,
    ): HttpClient {
        val httpClient = HttpClient(OkHttp) {
            engine {
                config {
                    addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY),
                    )
                        .addInterceptor(ChuckerInterceptor(context))
                        .addInterceptor(AuthInterceptor())
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
