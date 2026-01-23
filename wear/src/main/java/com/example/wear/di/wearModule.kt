package com.example.wear.di

import com.example.domain.usecases.ObserveLocationsUseCase
import com.example.domain.usecases.ObserveSelectedWeatherUseCase
import com.example.domain.usecases.RefreshSelectedWeatherUseCase
import com.example.domain.usecases.UpdateSelectedLocationUseCase
import com.example.wear.LocationsPresenterFactory
import com.example.wear.LocationsUiFactory
import com.example.wear.WearWeatherUiFactory
import com.example.wear.WeatherPresenterFactory
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import org.koin.dsl.module

val wearModule = module {
    // Use cases
    single { ObserveLocationsUseCase(get()) }
    single { ObserveSelectedWeatherUseCase(get(), get(), get()) }
    single { RefreshSelectedWeatherUseCase(get(), get(), get()) }
    single { UpdateSelectedLocationUseCase(get()) }

    // Presenter factories
    single<Presenter.Factory> { WeatherPresenterFactory(get(), get()) }
    single<Presenter.Factory> { LocationsPresenterFactory(get(), get()) }

    // UI factories
    single<Ui.Factory> { WearWeatherUiFactory() }
    single<Ui.Factory> { LocationsUiFactory() }

    // Circuit
    single {
        Circuit.Builder()
            .addPresenterFactories(getAll<Presenter.Factory>())
            .addUiFactories(getAll<Ui.Factory>())
            .build()
    }
}
