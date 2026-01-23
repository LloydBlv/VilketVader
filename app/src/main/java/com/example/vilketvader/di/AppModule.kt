package com.example.vilketvader.di

import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.domain.usecases.GetSelectedWeatherUseCase
import com.example.domain.usecases.GetWeatherUseCase
import com.example.domain.usecases.ObserveLocationsUseCase
import com.example.domain.usecases.ObserveSelectedLocationUseCase
import com.example.domain.usecases.ObserveSelectedWeatherUseCase
import com.example.domain.usecases.RefreshSelectedWeatherUseCase
import com.example.domain.usecases.UpdateSelectedLocationUseCase
import com.example.domain.utils.DateFormatter
import com.example.domain.utils.LanguageProvider
import com.example.home.HomePresenterFactory
import com.example.home.drawer.HomeUiFactory
import com.example.vilketvader.AndroidLanguageProvider
import com.example.vilketvader.DateFormatterDefault
import com.example.weather.WeatherPresenterFactory
import com.example.weather.WeatherUiFactory
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Language provider
    single<LanguageProvider> { AndroidLanguageProvider() }

    // Date formatter
    single<DateFormatter> { DateFormatterDefault() }

    // Use cases
    single { GetWeatherUseCase(get()) }
    single { GetSelectedWeatherUseCase(get(), get(), get()) }
    single { ObserveLocationsUseCase(get()) }
    single { ObserveSelectedLocationUseCase(get()) }
    single { ObserveSelectedWeatherUseCase(get(), get(), get()) }
    single { RefreshSelectedWeatherUseCase(get(), get(), get()) }
    single { UpdateSelectedLocationUseCase(get()) }

    // Widget
    single { GlanceAppWidgetManager(androidContext()) }

    // Presenter factories
    single<Presenter.Factory> { WeatherPresenterFactory(get(), get()) }
    single<Presenter.Factory> { HomePresenterFactory(get(), get()) }

    // UI factories
    single<Ui.Factory> { WeatherUiFactory() }
    single<Ui.Factory> { HomeUiFactory() }

    // Circuit
    single {
        Circuit.Builder()
            .addPresenterFactories(getAll<Presenter.Factory>())
            .addUiFactories(getAll<Ui.Factory>())
            .build()
    }
}
