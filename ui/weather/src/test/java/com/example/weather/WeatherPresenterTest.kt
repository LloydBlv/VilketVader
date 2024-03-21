package com.example.weather

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.example.domain.GetWeatherUseCase
import com.example.screens.WeatherScreen
import com.example.testing.WeatherRepositoryFake
import com.example.testing.assertTestWeather
import com.slack.circuit.test.test
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class WeatherPresenterTest {
    @Test
    fun `test presenter emits success state when usecase succeeds`() = runTest {
        val usecase = GetWeatherUseCase(
            weatherRepository = WeatherRepositoryFake()
        )
        val presenter =
            WeatherPresenter(getWeatherUseCase = usecase, screen = WeatherScreen("stockholm"))
        presenter.test {
            assertThat(awaitItem()).all {
                prop(WeatherUiState::isLoading).isTrue()
                prop(WeatherUiState::weather).isNull()
                prop(WeatherUiState::failure).isNull()
            }
            assertThat(awaitItem()).all {
                prop(WeatherUiState::isLoading).isFalse()
                transform { it.weather!! }.assertTestWeather()
                prop(WeatherUiState::failure).isNull()
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test presenter emits failed state when usecase fails`() = runTest {
        val usecase = GetWeatherUseCase(
            weatherRepository = WeatherRepositoryFake().apply {
                exception = Exception("Test exception")
            }
        )
        val presenter =
            WeatherPresenter(getWeatherUseCase = usecase, screen = WeatherScreen("stockholm"))
        presenter.test {
            assertThat(awaitItem()).all {
                prop(WeatherUiState::isLoading).isTrue()
                prop(WeatherUiState::weather).isNull()
                prop(WeatherUiState::failure).isNull()
            }
            assertThat(awaitItem()).all {
                prop(WeatherUiState::isLoading).isFalse()
                prop(WeatherUiState::weather).isNull()
                transform { it.failure!!.message }.isEqualTo("Test exception")
            }
            ensureAllEventsConsumed()
        }
    }
}