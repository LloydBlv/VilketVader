package com.example.home

import androidx.compose.runtime.Stable
import com.example.home.drawer.UiLocation
import com.slack.circuit.runtime.CircuitUiEvent

@Stable
sealed interface HomeUiEvents : CircuitUiEvent {
    data class OnLocationSelected(val location: UiLocation) : HomeUiEvents
}
