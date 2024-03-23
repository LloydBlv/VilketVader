package com.example.vilketvader

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class VilketVaderApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this@VilketVaderApplication)
            .crossfade(true)
            .components {
                add(WeatherIconMapper())
            }
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}