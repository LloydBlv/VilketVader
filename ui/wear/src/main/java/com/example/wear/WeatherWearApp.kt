package com.example.wear

import android.app.Application
import android.content.Context
import com.example.imageloading.WeatherImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherWearApp : Application(), WeatherImageLoaderFactory {
    override fun getContext(): Context {
        return this@WeatherWearApp
    }
}
