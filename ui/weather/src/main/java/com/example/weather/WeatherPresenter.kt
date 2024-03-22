package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.domain.GetSelectedLocationUseCase
import com.example.domain.GetWeatherUseCase
import com.example.domain.Location
import com.example.screens.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.components.SingletonComponent
import java.util.Locale

class WeatherPresenter @AssistedInject constructor(
    @Assisted private val screen: WeatherScreen,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSelectedLocationUseCase: GetSelectedLocationUseCase,
) : Presenter<WeatherUiState> {
    @Composable
    override fun present(): WeatherUiState {
        val selectedLocation by getSelectedLocationUseCase.flow.collectAsRetainedState(initial = null)
        val state by getWeatherUseCase.flow.collectAsRetainedState(initial = null)
        LaunchedEffect(key1 = Unit) {
            getSelectedLocationUseCase.invoke(GetSelectedLocationUseCase.Params(forceFresh = false))
        }
        LaunchedEffect(key1 = selectedLocation?.getOrNull()) {
            selectedLocation?.getOrNull()?.let { location: Location ->
                getWeatherUseCase.invoke(
                    GetWeatherUseCase.Params(
                        location = location,
                        language = Locale.getDefault().language
                    )
                )
            }

        }
        return WeatherUiState(
            isLoading = state == null,
            weather = state?.getOrNull(),
            failure = state?.exceptionOrNull()
        )
    }
}

@CircuitInject(WeatherScreen::class, SingletonComponent::class)
@AssistedFactory
interface Factory {
    fun create(screen: WeatherScreen): WeatherPresenter
}