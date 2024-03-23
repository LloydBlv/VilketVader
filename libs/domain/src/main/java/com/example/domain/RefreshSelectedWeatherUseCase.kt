package com.example.domain

import java.util.Locale
import javax.inject.Inject

class RefreshSelectedWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
): Interactor<Unit, Unit>() {
    override suspend fun doWork(params: Unit) {
        val selectedLocation = locationRepository.getSelectedLocation()
        if (selectedLocation != null) {
            weatherRepository.getWeather(selectedLocation, Locale.getDefault().language, forceFresh = true)
        }
    }
}