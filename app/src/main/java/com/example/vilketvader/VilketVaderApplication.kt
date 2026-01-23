package com.example.vilketvader

import android.app.Application
import android.content.Context
import com.example.data.di.androidDataModule
import com.example.data.di.dataModule
import com.example.imageloading.WeatherImageLoaderFactory
import com.example.vilketvader.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class VilketVaderApplication : Application(), WeatherImageLoaderFactory {
    override fun getContext(): Context {
        return this@VilketVaderApplication
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@VilketVaderApplication)
            modules(
                dataModule,
                androidDataModule,
                appModule,
            )
        }
    }
}
