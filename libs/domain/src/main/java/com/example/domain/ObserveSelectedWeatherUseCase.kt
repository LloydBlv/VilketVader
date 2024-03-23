package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class ObserveSelectedWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : SubjectInteractor<ObserveSelectedWeatherUseCase.Params, Result<Weather>>() {
    data class Params(val forceFresh: ForceFresh = ForceFresh.idle())

    override fun createObservable(params: Params): Flow<Result<Weather>> {
        return locationRepository.observeSelectedLocation()
            .flatMapLatest {
                if (it != null) {
                    weatherRepository.observeWeather(
                        location = it,
                        language = Locale.getDefault().language
                    )
                } else {
                    throw NoLocationSelectedException()
                }
            }
            .map {
                Result.success(it ?: Weather.EMPTY)
            }
            .catch { emit(Result.failure(it)) }
    }
}