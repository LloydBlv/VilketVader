package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherDatabase
import com.example.data.datasource.local.toDomain
import com.example.data.datasource.local.toEntity
import com.example.testing.WeatherRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class WeatherDatabaseTest {
    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.weatherDao()
    }

    @Test
    fun `test inserting weather result in correct retrieve`() = runTest {
        val repository = WeatherRepositoryFake()
        val weather = repository.getWeather("Stockholm", language = "en")
        dao.insertWeather(weather.toEntity())
        val result = dao.getWeather(weather.location.id)
        assertThat(result).isEqualTo(weather.toEntity())
    }
    @Test
    fun `test inserting weather result in correct domain returned after mapping`() = runTest {
        val repository = WeatherRepositoryFake()
        val weather = repository.getWeather("Stockholm", language = "en")
        dao.insertWeather(weather.toEntity())
        val result = dao.getWeather(weather.location.id)
        assertThat(result.toDomain()).isEqualTo(weather)
    }
    @Test
    fun `test inserting duplicates results in overriding`() = runTest {
        val weather = WeatherRepositoryFake().getWeather("Stockholm", language = "en")
        val entity = weather.toEntity()
        dao.insertWeather(entity)
        assertThat(dao.getWeather(weather.location.id)).isEqualTo(entity)
        dao.insertWeather(weather.toEntity())
        assertThat(dao.getWeather(weather.location.id)).isEqualTo(entity)
    }
    @Test
    fun `test delete works and record gets removed after deletion`() = runTest {
        val weather = WeatherRepositoryFake().getWeather("Stockholm", language = "en")
        val entity = weather.toEntity()
        dao.insertWeather(entity)
        assertThat(dao.getWeather(weather.location.id)).isEqualTo(entity)
        dao.deleteWeather(entity)
        assertThat(dao.getWeather(weather.location.id)).isNull()
    }
    @Test
    fun `test delete all works and database gets cleared afterwards`() = runTest {
        val weather = WeatherRepositoryFake().getWeather("Stockholm", language = "en")
        val entity = weather.toEntity()
        dao.insertWeather(entity)
        dao.insertWeather(entity.copy(locationId = 2))
        dao.insertWeather(entity.copy(locationId = 3))
        assertThat(dao.getAllWeathers()).hasSize(3)
        dao.deleteAll()
        assertThat(dao.getAllWeathers()).hasSize(0)
    }

    @After
    fun tearDown() {
        database.close()
    }
}