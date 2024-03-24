package com.example.domain

import java.util.Locale
import javax.inject.Inject

class GetSelectedWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : Interactor<GetSelectedWeatherUseCase.Params, Weather>() {
    data class Params(val forceFresh: ForceFresh = ForceFresh.idle())

    override suspend fun doWork(params: Params): Weather {
        val location = locationRepository.getSelectedLocation()
        return if (location != null) {
            weatherRepository.getWeather(
                forceFresh = params.forceFresh.shouldRefresh,
                location = location,
                language = Locale.getDefault().language
            )
        } else {
            throw NoLocationSelectedException()
        }
    }
}