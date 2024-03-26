package com.example.domain.usecases

import co.touchlab.kermit.Logger
import com.example.domain.repositories.LocationRepository
import com.example.domain.utils.SubjectInteractor
import com.example.domain.models.Weather
import com.example.domain.repositories.WeatherRepository
import com.example.domain.models.WeatherResult
import com.example.domain.models.ForceFresh
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest
import java.util.Locale
import javax.inject.Inject

class ObserveSelectedWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
) : SubjectInteractor<ObserveSelectedWeatherUseCase.Params, WeatherResult<Weather>>() {
    data class Params(val forceFresh: ForceFresh = ForceFresh.idle())

    override fun createObservable(params: Params): Flow<WeatherResult<Weather>> {
        return locationRepository.observeSelectedLocation()
            .transformLatest {
                Logger.withTag("WeatherPresenter").i { "location changed to $it" }
                emit(WeatherResult.loading<Weather>())
                emitAll(weatherRepository.observeWeather(
                    location = it,
                    language = Locale.getDefault().language,
                    forceFresh = params.forceFresh.shouldRefresh
                ).map {
                    Logger.withTag("WeatherPresenter").i { "weather changed to $it" }
                    WeatherResult.success(it ?: Weather.EMPTY)
                })
            }
            .catch { emit(WeatherResult.failure(it)) }
    }
}
