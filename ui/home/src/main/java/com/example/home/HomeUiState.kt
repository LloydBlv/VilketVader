package com.example.home

import androidx.compose.runtime.Immutable
import com.example.home.drawer.UiLocation
import com.slack.circuit.runtime.CircuitUiState
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeUiState(
    val isLoading: Boolean,
    val locations: ImmutableList<UiLocation>,
    val evenSink: (HomeUiEvents) -> Unit,
) : CircuitUiState
