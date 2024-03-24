package com.example.weather

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.RefreshSelectedWeatherUseCase
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
        val weatherRepository = WeatherRepositoryFake()
        val locationRepository = FakeLocationRepository(
            mutableListOf(TestData.STOCKHOLM.copy(isSelected = true))
        )
        val presenter =
            WeatherPresenter(
                observeSelectedWeather = ObserveSelectedWeatherUseCase(
                    locationRepository = locationRepository,
                    weatherRepository = weatherRepository
                ),
                refreshSelectedWeather = RefreshSelectedWeatherUseCase(
                    weatherRepository = weatherRepository,
                    locationRepository = locationRepository
                )
            )
        presenter.test {
            assertThat(awaitItem()).isInstanceOf(WeatherUiState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(WeatherUiState.Success::class.java)
                .transform { it.weather }
                .assertTestWeather()
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `test presenter emits failed state when usecase fails`() = runTest {
        val weatherRepository = WeatherRepositoryFake().apply {
            exception = Exception("Test exception")
        }
        val locationRepository = FakeLocationRepository(
            mutableListOf(TestData.STOCKHOLM.copy(isSelected = true))
        )
        val presenter =
            WeatherPresenter(
                observeSelectedWeather = ObserveSelectedWeatherUseCase(
                    locationRepository = locationRepository,
                    weatherRepository = weatherRepository
                ),
                refreshSelectedWeather = RefreshSelectedWeatherUseCase(
                    weatherRepository = weatherRepository,
                    locationRepository = locationRepository
                )
            )
        presenter.test {
            assertThat(awaitItem()).isInstanceOf(WeatherUiState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(WeatherUiState.Failure::class.java)
                .transform { it.error!!.message }
                .isNotNull()
                .isEqualTo("Test exception")
            ensureAllEventsConsumed()
        }
    }
}