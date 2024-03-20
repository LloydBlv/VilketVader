package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) : SubjectInteractor<GetWeatherUseCase.Params, Result<Weather>>() {
    data class Params(val location: String)

    override fun createObservable(params: Params): Flow<Result<Weather>> {
        return flow { emit(weatherRepository.getWeather(params.location)) }
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}