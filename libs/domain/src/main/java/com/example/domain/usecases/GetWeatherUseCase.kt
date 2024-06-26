package com.example.domain.usecases

import com.example.domain.utils.SubjectInteractor
import com.example.domain.models.Weather
import com.example.domain.repositories.WeatherRepository
import com.example.domain.models.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) : SubjectInteractor<GetWeatherUseCase.Params, Result<Weather>>() {
    data class Params(val location: Location, val language: String)

    override fun createObservable(params: Params): Flow<Result<Weather>> {
        return flow { emit(weatherRepository.getWeather(params.location, params.language)) }
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
