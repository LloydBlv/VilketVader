package com.example.wear

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.domain.models.Location
import com.example.domain.usecases.ObserveLocationsUseCase
import com.example.domain.usecases.UpdateSelectedLocationUseCase
import com.example.wear.LocationsScreen.UiState
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data object LocationsScreen : Screen {
    data class UiState(
        val isLoading: Boolean,
        val locations: ImmutableList<Location>,
        val eventSink: (Events) -> Unit,
    ) : CircuitUiState

    sealed interface Events : CircuitUiEvent {
        data class OnLocationClicked(val location: Location) : Events
    }
}

class LocationsPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val observeLocations: ObserveLocationsUseCase,
    private val selectedLocationUseCase: UpdateSelectedLocationUseCase,
) : Presenter<UiState> {
    @Composable
    override fun present(): UiState {
        val locations by observeLocations.flow.collectAsRetainedState(initial = null)
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            observeLocations.invoke(ObserveLocationsUseCase.Params())
        }
        fun eventSink(events: LocationsScreen.Events) {
            when (events) {
                is LocationsScreen.Events.OnLocationClicked -> {
                    scope.launch {
                        selectedLocationUseCase.invoke(
                            UpdateSelectedLocationUseCase.Params(
                                events.location.id,
                            ),
                        )
                    }
                    navigator.goTo(WeatherScreen)
                }
            }
        }
        return UiState(
            isLoading = locations == null,
            locations = locations?.getOrNull().orEmpty().toPersistentList(),
            eventSink = ::eventSink,
        )
    }
}

@CircuitInject(LocationsScreen::class, SingletonComponent::class)
@AssistedFactory
interface Factory {
    fun create(navigator: Navigator): LocationsPresenter
}
