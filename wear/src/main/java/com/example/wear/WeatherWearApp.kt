package com.example.wear

import android.app.Application
import android.content.Context
import com.example.data.di.androidDataModule
import com.example.data.di.dataModule
import com.example.imageloading.WeatherImageLoaderFactory
import com.example.wear.di.wearModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherWearApp : Application(), WeatherImageLoaderFactory {
    override fun getContext(): Context {
        return this@WeatherWearApp
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherWearApp)
            modules(
                dataModule,
                androidDataModule,
                wearModule,
            )
        }
    }
}
