package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.example.data.datasource.local.LocalDataSourceDefault
import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.PrefillHelper
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherDatabase
import com.example.data.datasource.local.getInitialLocations
import com.example.data.datasource.local.toDomain
import com.example.data.repositories.LocationRepositoryDefault
import com.example.data.repositories.WeatherRepositoryDefault
import com.example.domain.ObserveLocationsUseCase
import com.example.domain.LocationRepository
import com.example.domain.UpdateSelectedLocationUseCase
import com.example.domain.WeatherRepository
import com.example.testing.TestData
import com.example.testing.WeatherClientFake
import com.example.testing.assertTestWeather
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WeatherSelectionTests {
    private lateinit var datasource: LocalDataSourceDefault
    private lateinit var locationDao: LocationDao
    private lateinit var weatherDao: WeatherDao
    private lateinit var locationRepository: LocationRepository
    private lateinit var weatherRepository: WeatherRepository
    @Before
    fun setup() {
        val database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        val dao = database.weatherDao()
        locationDao = database.locationDao()
        weatherDao = database.weatherDao()
        datasource = LocalDataSourceDefault(dao)
        runBlocking { PrefillHelper(Lazy { locationDao }).prefill(getInitialLocations()) }
        locationRepository = LocationRepositoryDefault(locationDao)
        weatherRepository = WeatherRepositoryDefault(
            client = WeatherClientFake(),
            localDataSource = datasource
        )
    }

    @Test
    fun `test selecting location`() = runTest {
        val getLocationsUseCase = ObserveLocationsUseCase(locationRepository)
        getLocationsUseCase.flow.test {
            getLocationsUseCase.invoke(ObserveLocationsUseCase.Params(true))
            assertThat(awaitItem().getOrNull()!!).all {
                hasSize(2)
                isEqualTo(getInitialLocations().map { it.toDomain() })
            }
            ensureAllEventsConsumed()
        }
        assertThat(weatherRepository.getWeather(location = TestData.STOCKHOLM, language = "en"))
            .assertTestWeather()
        assertThat(datasource.getWeather(TestData.STOCKHOLM.id))
            .isNotNull()
            .assertTestWeather()
        UpdateSelectedLocationUseCase(locationRepository).invoke(
            params = UpdateSelectedLocationUseCase.Params(TestData.ZURICH.id)
        )
        assertThat(datasource.getWeather(TestData.STOCKHOLM.id))
            .isNotNull()
            .assertTestWeather()
        getLocationsUseCase.flow.test {
            with(awaitItem().getOrNull()!!) {
                assertThat(this).hasSize(2)
                find { it.name == TestData.ZURICH.name }?.let {
                    assertThat(it.isSelected).isEqualTo(true)
                }
            }
            ensureAllEventsConsumed()
        }
        assertThat(weatherRepository.getWeather(location = TestData.ZURICH, language = "en"))
            .isNotNull()
        assertThat(datasource.getWeather(TestData.ZURICH.id))
            .isNotNull()
        assertThat(datasource.getWeather(TestData.STOCKHOLM.id))
            .isNotNull()
    }

}