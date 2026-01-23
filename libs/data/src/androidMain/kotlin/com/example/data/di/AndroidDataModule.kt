package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.data.AuthInterceptor
import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.LocationDataSource
import com.example.data.datasource.WeatherApiClient
import com.example.data.datasource.WeatherApiClientDefault
import com.example.data.datasource.local.LocalDataSourceDefault
import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.LocationDataSourceDefault
import com.example.data.datasource.local.PrefillDatabase
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherDatabase
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidDataModule = module {
    single {
        Json {
            isLenient = true
            explicitNulls = false
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    single {
        HttpClient(OkHttp) {
            engine {
                config {
                    addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY),
                    )
                        .addInterceptor(ChuckerInterceptor(androidContext()))
                        .addInterceptor(AuthInterceptor())
                }
            }
            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single {
        Ktorfit.Builder()
            .httpClient(get<HttpClient>())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
    }

    single<WeatherApiClient> {
        WeatherApiClientDefault(
            ktorfitProvider = { get() }
        )
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather.db",
        ).addCallback(get<PrefillDatabase>())
            .build()
    }

    single { PrefillHelper { get<WeatherDatabase>().locationDao() } }

    single { PrefillDatabase { get() } }

    single<WeatherDao> { get<WeatherDatabase>().weatherDao() }

    single<LocationDao> { get<WeatherDatabase>().locationDao() }

    single<LocalDataSource> { LocalDataSourceDefault(get()) }

    single<LocationDataSource> { LocationDataSourceDefault(get()) }
}
