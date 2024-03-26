package com.example.domain.usecases

import com.example.domain.repositories.LocationRepository
import com.example.domain.repositories.WeatherRepository
import com.example.domain.utils.Interactor
import java.util.Locale
import javax.inject.Inject

class RefreshSelectedWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
): Interactor<Unit, Unit>() {
    override suspend fun doWork(params: Unit) {
        val selectedLocation = locationRepository.getSelectedLocation()
        if (selectedLocation != null) {
            weatherRepository.refresh(selectedLocation, Locale.getDefault().language)
        }
    }
}
