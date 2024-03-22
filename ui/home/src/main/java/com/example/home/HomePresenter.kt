package com.example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.domain.GetLocationsUseCase
import com.example.home.drawer.UiLocation
import com.example.screens.HomeScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class HomeUiState(
    val isLoading: Boolean,
    val locations: ImmutableList<UiLocation>
) : CircuitUiState


class HomePresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val getLocationsUseCase: GetLocationsUseCase
) : Presenter<HomeUiState> {
    @Composable
    override fun present(): HomeUiState {
        val locations by getLocationsUseCase.flow.collectAsRetainedState(initial = null)
        LaunchedEffect(key1 = Unit) {
            getLocationsUseCase.invoke(GetLocationsUseCase.Params(false))
        }
        return HomeUiState(
            isLoading = locations == null,
            locations = locations?.getOrNull().orEmpty().map {
                UiLocation(cityName = it.name, isSelected = it.isSelected)
            }.toPersistentList()
        )
    }
}

@CircuitInject(HomeScreen::class, SingletonComponent::class)
@AssistedFactory
interface Factory {
    fun create(navigator: Navigator): HomePresenter
}