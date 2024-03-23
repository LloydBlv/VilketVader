package com.example.weather

import com.example.domain.Weather
import com.slack.circuit.runtime.CircuitUiState

data class WeatherUiState(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val weather: Weather? = null,
    val failure: Throwable? = null,
    val eventSink: (WeatherEvent) -> Unit = { }
): CircuitUiState


sealed interface WeatherEvent {
    data object Refresh : WeatherEvent
}