package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class GetSelectedWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : SubjectInteractor<GetSelectedWeatherUseCase.Params, Result<Weather>>() {
    data class Params(val forceFresh: ForceFresh = ForceFresh.idle())

    override fun createObservable(params: Params): Flow<Result<Weather>> {
        return locationRepository.observeSelectedLocation()
            .map {
                if (it != null) {
                    weatherRepository.getWeather(
                        forceFresh = params.forceFresh.shouldRefresh,
                        location = it,
                        language = Locale.getDefault().language
                    )
                } else {
                    throw NoLocationSelectedException()
                }
            }
            .map {
                Result.success(it)
            }
            .catch { emit(Result.failure(it)) }
    }
}