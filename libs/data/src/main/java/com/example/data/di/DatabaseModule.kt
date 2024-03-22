package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.PrefillDatabase
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {
    companion object {
        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context,
            prefillDatabase: PrefillDatabase
        ): WeatherDatabase {
            return Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "weather.db"
            ).addCallback(prefillDatabase)
                .build()
        }

        @Provides
        fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
            return weatherDatabase.weatherDao()
        }

        @Provides
        fun provideLocationDao(weatherDatabase: WeatherDatabase): LocationDao {
            return weatherDatabase.locationDao()
        }
    }

}