package com.example.vilketvader

import android.app.Application
import android.content.Context
import com.example.imageloading.WeatherImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class VilketVaderApplication : Application(), WeatherImageLoaderFactory {
    override fun getContext(): Context {
        return this@VilketVaderApplication
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
