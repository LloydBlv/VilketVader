package com.example.imageloading

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory

interface WeatherImageLoaderFactory : ImageLoaderFactory {

    fun getContext(): Context
    override fun newImageLoader(): ImageLoader {
        return WeatherImageLoader.createImageLoader(getContext())
    }
}
