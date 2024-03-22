package com.example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.domain.GetLocationsUseCase
import com.example.domain.Location
import com.example.domain.UpdateSelectedLocationUseCase
import com.example.home.drawer.UiLocation
import com.example.screens.HomeScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

class HomePresenter @AssistedInject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val updateSelectedLocation: UpdateSelectedLocationUseCase
) : Presenter<HomeUiState> {
    @Composable
    override fun present(): HomeUiState {
        val scope = rememberCoroutineScope()
        val locations by getLocationsUseCase.flow.collectAsRetainedState(initial = null)
        LaunchedEffect(key1 = Unit) {
            getLocationsUseCase.invoke(GetLocationsUseCase.Params(false))
        }
        fun eventSink(event: HomeUiEvents) {
            when (event) {
                is HomeUiEvents.OnLocationSelected -> {
                    scope.launch {
                        val params = UpdateSelectedLocationUseCase.Params(event.location.id)
                        updateSelectedLocation.invoke(params)
                    }
                }
            }
        }
        return HomeUiState(
            isLoading = locations == null,
            locations = locations?.getOrNull()
                .orEmpty().map(Location::toUiLocation)
                .toPersistentList(),
            evenSink = ::eventSink
        )
    }
}

private fun Location.toUiLocation(): UiLocation {
    return UiLocation(cityName = name, isSelected = isSelected, id = id)
}

@CircuitInject(HomeScreen::class, SingletonComponent::class)
@AssistedFactory
interface Factory {
    fun create(): HomePresenter
}