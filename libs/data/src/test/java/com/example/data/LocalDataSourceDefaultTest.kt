package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.example.data.datasource.local.LocalDataSourceDefault
import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherDatabase
import com.example.data.datasource.local.toEntity
import com.example.domain.Weather
import com.example.testing.TestData
import com.example.testing.WeatherRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class LocalDataSourceDefaultTest {
    private lateinit var datasource: LocalDataSourceDefault
    private lateinit var locationDao: LocationDao
    private lateinit var weatherDao: WeatherDao
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
    }

    @Test
    fun `test inserting weather result in correct retrieve`() = runTest {
        val weather: Weather = getWeather()
        locationDao.insertOrUpdateLocationWithSelection(weather.location.toEntity())
        datasource.updateWeather(weather)
        val result = datasource.getWeather(weather.location.id)
        val selectedLocation = weather.location.copy(isSelected = true)
        assertThat(result).isNotNull()
            .isEqualTo(weather.copy(location = selectedLocation))
    }

    @Test
    fun `test inserting two different weathers result in correct retrieve`() = runTest {
        val repositoryFake = WeatherRepositoryFake()
        val stockholm = repositoryFake.getWeather(TestData.STOCKHOLM, language = "en")
        val zurich = repositoryFake.getWeather(TestData.ZURICH, language = "en")
        locationDao.insertOrUpdateLocationWithSelection(stockholm.location.toEntity())
        locationDao.insertOrUpdateLocationWithSelection(zurich.location.toEntity())

        datasource.updateWeather(stockholm)
        assertThat(datasource.getWeather(stockholm.location.id)).all {
            isNotNull()
                .isEqualTo(stockholm.copy(location = stockholm.location.copy(isSelected = true)))

        }

        datasource.updateWeather(zurich)
        assertThat(datasource.getWeather(zurich.location.id)).all {
            isNotNull()
                .isEqualTo(zurich.copy(location = zurich.location.copy(isSelected = false)))
        }
        assertThat(datasource.getWeather(stockholm.location.id)).all {
            isNotNull()
                .isEqualTo(stockholm.copy(location = stockholm.location.copy(isSelected = true)))

        }
        assertThat(weatherDao.getAllWeathers()).hasSize(2)
    }

    @Test
    fun `test inserting duplicates results in overriding`() = runTest {
        val weather = getWeather()
        locationDao.insertOrUpdateLocationWithSelection(weather.location.toEntity())
        val selectedLocation = weather.location.copy(isSelected = true)
        datasource.updateWeather(weather)
        assertThat(datasource.getWeather(weather.location.id)).isEqualTo(weather.copy(location = selectedLocation))
        datasource.updateWeather(weather)
        assertThat(datasource.getWeather(weather.location.id)).isEqualTo(weather.copy(location = selectedLocation))
    }

    private suspend fun getWeather() =
        WeatherRepositoryFake().getWeather(TestData.STOCKHOLM, language = "en")
}