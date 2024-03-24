package com.example.wear

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.RefreshSelectedWeatherUseCase
import com.example.domain.Weather
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data object WeatherScreen : Screen {

    data class UiState(
        val isLoading: Boolean,
        val weather: Weather?,
        val failure: Throwable? = null,
        val eventSink: (Events) -> Unit
    ) : CircuitUiState

    sealed interface Events {
        data object Refresh : Events
    }
}

class WeatherPresenter @AssistedInject constructor(
    private val refreshSelectedWeatherUseCase: RefreshSelectedWeatherUseCase,
    private val observeSelectedWeather: ObserveSelectedWeatherUseCase
) : Presenter<WeatherScreen.UiState> {
    @Composable
    override fun present(): WeatherScreen.UiState {
        val state by observeSelectedWeather.flow.collectAsRetainedState(initial = null)
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            observeSelectedWeather.invoke(ObserveSelectedWeatherUseCase.Params())
        }

        fun eventSink(event: WeatherScreen.Events) {
            when (event) {
                is WeatherScreen.Events.Refresh -> {
                    scope.launch { refreshSelectedWeatherUseCase.invoke(Unit) }
                }
            }
        }

        return WeatherScreen.UiState(
            isLoading = state == null,
            weather = state?.getOrNull(),
            eventSink = ::eventSink
        )
    }
    @CircuitInject(WeatherScreen::class, SingletonComponent::class)
    @AssistedFactory
    interface Factory {
        fun create(): WeatherPresenter
    }
}

