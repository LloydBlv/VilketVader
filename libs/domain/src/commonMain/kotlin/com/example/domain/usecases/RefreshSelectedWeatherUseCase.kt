package com.example.domain.usecases

import com.example.domain.repositories.LocationRepository
import com.example.domain.repositories.WeatherRepository
import com.example.domain.utils.Interactor
import com.example.domain.utils.LanguageProvider

class RefreshSelectedWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val languageProvider: LanguageProvider,
) : Interactor<Unit, Unit>() {
    override suspend fun doWork(params: Unit) {
        val selectedLocation = locationRepository.getSelectedLocation()
        if (selectedLocation != null) {
            weatherRepository.refresh(selectedLocation, languageProvider.getLanguage())
        }
    }
}
