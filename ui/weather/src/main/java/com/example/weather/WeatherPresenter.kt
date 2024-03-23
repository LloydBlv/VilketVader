package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.RefreshSelectedWeatherUseCase
import com.example.domain.invoke
import com.example.screens.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch

class WeatherPresenter @AssistedInject constructor(
    private val observeSelectedWeather: ObserveSelectedWeatherUseCase,
    private val refreshSelectedWeather: RefreshSelectedWeatherUseCase
) : Presenter<WeatherUiState> {
    @Composable
    override fun present(): WeatherUiState {
        val weather by observeSelectedWeather.flow.collectAsRetainedState(initial = null)
        val isRefreshing by refreshSelectedWeather.inProgress.collectAsRetainedState(initial = false)
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            observeSelectedWeather.invoke(ObserveSelectedWeatherUseCase.Params())
        }
        fun eventSink(event: WeatherEvent) {
            when (event) {
                WeatherEvent.Refresh -> {
                    scope.launch { refreshSelectedWeather.invoke() }
                }
            }
        }
        return WeatherUiState(
            isLoading = weather == null || isRefreshing,
            weather = weather?.getOrNull(),
            failure = weather?.exceptionOrNull(),
            eventSink = ::eventSink
        )
    }
}

@CircuitInject(WeatherScreen::class, SingletonComponent::class)
@AssistedFactory
interface Factory {
    fun create(): WeatherPresenter
}