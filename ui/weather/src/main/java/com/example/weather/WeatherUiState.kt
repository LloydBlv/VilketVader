package com.example.weather

import com.example.domain.Weather
import com.slack.circuit.runtime.CircuitUiState

sealed interface WeatherUiState : CircuitUiState {
    val eventSink: (WeatherEvent) -> Unit

    data class Success(
        val weather: Weather,
        val isRefreshing: Boolean,
        override val eventSink: (WeatherEvent) -> Unit,
    ) : WeatherUiState

    data class Failure(
        val error: Throwable?,
        override val eventSink: (WeatherEvent) -> Unit,
    ) : WeatherUiState

    data object Loading : WeatherUiState {
        override val eventSink: (WeatherEvent) -> Unit = {}
    }
}

sealed interface WeatherEvent {
    data object Refresh : WeatherEvent
}
