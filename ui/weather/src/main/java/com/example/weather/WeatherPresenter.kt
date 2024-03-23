package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.domain.GetSelectedWeatherUseCase
import com.example.screens.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent

class WeatherPresenter @AssistedInject constructor(
    private val getSelectedWeatherUseCase: GetSelectedWeatherUseCase,
) : Presenter<WeatherUiState> {
    @Composable
    override fun present(): WeatherUiState {
        val weather by getSelectedWeatherUseCase.flow.collectAsRetainedState(initial = null)
        LaunchedEffect(key1 = Unit) {
            getSelectedWeatherUseCase.invoke(GetSelectedWeatherUseCase.Params(forceFresh = false))
        }
        return WeatherUiState(
            isLoading = weather == null,
            weather = weather?.getOrNull(),
            failure = weather?.exceptionOrNull()
        )
    }
}

@CircuitInject(WeatherScreen::class, SingletonComponent::class)
@AssistedFactory
interface Factory {
    fun create(): WeatherPresenter
}