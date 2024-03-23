package com.example.weather

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.example.domain.GetSelectedWeatherUseCase
import com.example.testing.FakeLocationRepository
import com.example.testing.TestData
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
        val presenter =
            WeatherPresenter(
                getSelectedWeatherUseCase = GetSelectedWeatherUseCase(
                    locationRepository = FakeLocationRepository(
                        mutableListOf(
                            TestData.STOCKHOLM.copy(
                                isSelected = true
                            )
                        )
                    ),
                    weatherRepository = WeatherRepositoryFake()
                )
            )
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
        val presenter =
            WeatherPresenter(
                getSelectedWeatherUseCase = GetSelectedWeatherUseCase(
                    locationRepository = FakeLocationRepository(mutableListOf(TestData.STOCKHOLM.copy(isSelected = true))),
                    weatherRepository = WeatherRepositoryFake().apply {
                        exception = Exception("Test exception")
                    }
                )
            )
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