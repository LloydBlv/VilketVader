package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.domain.GetWeatherUseCase
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter

class WeatherPresenter constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : Presenter<WeatherUiState> {
    @Composable
    override fun present(): WeatherUiState {
        val state by getWeatherUseCase.flow.collectAsRetainedState(initial = null)
        LaunchedEffect(key1 = Unit) {
            getWeatherUseCase.invoke(GetWeatherUseCase.Params(location = "stockholm"))
        }
        return WeatherUiState(
            isLoading = state == null,
            weather = state?.getOrNull(),
            failure = state?.exceptionOrNull()
        )
    }
}