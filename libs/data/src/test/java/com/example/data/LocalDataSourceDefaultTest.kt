package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.data.datasource.local.LocalDataSourceDefault
import com.example.data.datasource.local.WeatherDatabase
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

    @Before
    fun setup() {
        val database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        val dao = database.weatherDao()
        datasource = LocalDataSourceDefault(dao, database.locationDao())
    }

    @Test
    fun `test inserting weather result in correct retrieve`() = runTest {
        val weather = getWeather()
        datasource.updateWeather(weather)
        val result = datasource.getWeather(weather.location.id)
        assertThat(result).isEqualTo(weather)
    }

    @Test
    fun `test inserting duplicates results in overriding`() = runTest {
        val weather = getWeather()
        datasource.updateWeather(weather)
        assertThat(datasource.getWeather(weather.location.id)).isEqualTo(weather)
        datasource.updateWeather(weather)
        assertThat(datasource.getWeather(weather.location.id)).isEqualTo(weather)
    }

    private suspend fun getWeather() =
        WeatherRepositoryFake().getWeather(TestData.STOCKHOLM, language = "en")
}