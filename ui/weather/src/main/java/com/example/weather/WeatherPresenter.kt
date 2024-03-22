package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.domain.GetWeatherUseCase
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
    private val getWeatherUseCase: GetWeatherUseCase
) : Presenter<WeatherUiState> {
    @Composable
    override fun present(): WeatherUiState {
        val state by getWeatherUseCase.flow.collectAsRetainedState(initial = null)
        LaunchedEffect(key1 = Unit) {
            getWeatherUseCase.invoke(
                GetWeatherUseCase.Params(
                    location = screen.location,
                    language = Locale.getDefault().language
                )
            )
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