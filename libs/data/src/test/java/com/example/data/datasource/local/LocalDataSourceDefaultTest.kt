package com.example.data.datasource.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.prop
import com.example.domain.Weather
import com.example.testing.TestData
import com.example.testing.WeatherRepositoryFake
import com.example.testing.assertTestWeather
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalDataSourceDefaultTest {
    private lateinit var database: WeatherDatabase
    private lateinit var locationDao: LocationDao
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        locationDao = database.locationDao()
        weatherDao = database.weatherDao()
    }

    @Test
    fun `assert initially local data source returns null`() = runTest {
        val datasource = LocalDataSourceDefault(
            weatherDao = weatherDao
        )
        assertThat(datasource.getWeather(TestData.STOCKHOLM.id)).isNull()
        assertThat(datasource.getWeather(TestData.ZURICH.id)).isNull()
    }
    @Test
    fun `after update, the cache returns correct data`() = runTest {
        val repository = WeatherRepositoryFake()
        val locationStockholm = TestData.STOCKHOLM
        locationDao.insertOrUpdateLocationWithSelection(locationStockholm.toEntity())

        val stockholmWeather = repository.getWeather(locationStockholm, language = "en")
        assertThat(stockholmWeather).assertTestWeather()

        val datasource = LocalDataSourceDefault(
            weatherDao = weatherDao
        )
        assertThat(datasource.getWeather(locationStockholm.id)).isNull()
        datasource.updateWeather(stockholmWeather)
        assertThat(datasource.getWeather(locationStockholm.id)).isNotNull()
            .assertTestWeather()
    }
    @Test
    fun `after two updates, the cache returns correct data`() = runTest {
        val repository = WeatherRepositoryFake()
        val stockholm = TestData.STOCKHOLM
        locationDao.insertOrUpdateLocationWithSelection(stockholm.toEntity())
        val stockholmWeather = repository.getWeather(stockholm, language = "en")

        val zurich = TestData.ZURICH
        locationDao.insertOrUpdateLocationWithSelection(zurich.toEntity())

        val zurichWeather = repository.getWeather(zurich, language = "en")

        val datasource = LocalDataSourceDefault(
            weatherDao = weatherDao
        )
        assertThat(datasource.getWeather(stockholm.id)).isNull()
        datasource.updateWeather(stockholmWeather)
        assertThat(datasource.getWeather(stockholm.id)).isNotNull()
            .assertTestWeather()

        assertThat(datasource.getWeather(zurich.id)).isNull()
        datasource.updateWeather(zurichWeather)
        assertThat(datasource.getWeather(zurich.id)).isNotNull()
            .prop(Weather::location).isEqualTo(zurich)
    }
}