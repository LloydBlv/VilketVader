package com.example.imageloading

import android.content.Context
import coil.ImageLoader

object WeatherImageLoader {
    fun createImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .components {
                add(WeatherIconMapper())
            }
            .build()
    }
}
