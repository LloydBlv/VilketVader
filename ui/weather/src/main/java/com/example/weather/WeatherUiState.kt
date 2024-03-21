package com.example.weather

import com.example.domain.Weather
import com.slack.circuit.runtime.CircuitUiState

data class WeatherUiState(
    val isLoading: Boolean,
    val weather: Weather? = null,
    val failure: Throwable? = null
): CircuitUiState