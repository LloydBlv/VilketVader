package com.example.screens

import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object HomeScreen : Screen

@Parcelize
data class WeatherScreen(val location: String) : Screen