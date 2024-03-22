package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToWithGivenProperties
import assertk.assertions.isNull
import assertk.assertions.support.appendName
import com.example.data.datasource.local.WeatherAndLocation
import com.example.data.datasource.local.WeatherDao
import com.example.data.datasource.local.WeatherDatabase
import com.example.data.datasource.local.WeatherEntity
import com.example.data.datasource.local.toDomain
import com.example.data.datasource.local.toEntity
import com.example.testing.TestData
import com.example.testing.WeatherRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.KProperty1


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
        val weather = repository.getWeather(TestData.STOCKHOLM, language = "en")
        database.locationDao().insertLocation(weather.location.toEntity())
        dao.insertWeather(weather.toEntity())
        val result: WeatherEntity = dao.getWeather(weather.location.id)?.weather!!
        assertThat(result).isEqualToIgnoringId(weather.toEntity())
    }


    @Test
    fun `test inserting weather result in correct domain returned after mapping`() = runTest {
        val repository = WeatherRepositoryFake()
        val weather = repository.getWeather(TestData.STOCKHOLM, language = "en")
        database.locationDao().insertLocation(weather.location.toEntity())
        dao.insertWeather(weather.toEntity())
        val result: WeatherAndLocation? = dao.getWeather(weather.location.id)
        assertThat(result?.toDomain()).isEqualTo(weather)
    }

    @Test
    fun `test inserting duplicates results in overriding`() = runTest {
        val weather = getWeather()
        val entity = weather.toEntity()
        database.locationDao().insertLocation(weather.location.toEntity())
        dao.insertWeather(entity)
        assertThat(dao.getWeather(weather.location.id)?.weather!!).isEqualToIgnoringId(entity)
        dao.insertWeather(weather.toEntity())
        assertThat(dao.getWeather(weather.location.id)?.weather!!).isEqualToIgnoringId(entity)
    }
    @Test
    fun `test location delete cascades and deletes weather too`() = runTest {
        val weather = getWeather()
        val entity = weather.toEntity()
        database.locationDao().insertLocation(weather.location.toEntity())
        dao.insertWeather(entity)
        assertThat(dao.getWeather(weather.location.id)?.weather!!).isEqualToIgnoringId(entity)
        assertThat(dao.getAllWeathers()).hasSize(1)
        database.locationDao().deleteLocation(weather.location.toEntity())
        assertThat(dao.getWeather(weather.location.id)?.weather).isNull()
        assertThat(dao.getAllWeathers()).isEmpty()
    }

    @Test
    fun `test delete works and record gets removed after deletion`() = runTest {
        val weather = getWeather()
        val entity = weather.toEntity()
        database.locationDao().insertLocation(weather.location.toEntity())
        dao.insertWeather(entity)
        val entityAfterInsert = dao.getWeather(weather.location.id)?.weather!!
        assertThat(entityAfterInsert).isEqualToIgnoringId(entity)
        dao.deleteWeather(entityAfterInsert)
        assertThat(dao.getWeather(weather.location.id)?.weather).isNull()
        assertThat(dao.getWeather(weather.location.id)).isNull()
        assertThat(dao.getWeather(weather.location.id)?.location).isNull()
    }

    @Test
    fun `test delete all works and database gets cleared afterwards`() = runTest {
        val weather = getWeather()
        val entity = weather.toEntity()
        database.locationDao().insertLocation(weather.location.toEntity())
        database.locationDao().insertLocation(weather.location.copy(id = 2).toEntity())
        database.locationDao().insertLocation(weather.location.copy(id = 3).toEntity())
        dao.insertWeather(entity)
        dao.insertWeather(entity.copy(locationId = 2))
        dao.insertWeather(entity.copy(locationId = 3))
        assertThat(dao.getAllWeathers()).hasSize(3)
        dao.deleteAll()
        assertThat(database.locationDao().getAllLocations()).hasSize(3)
        assertThat(dao.getAllWeathers()).hasSize(0)
    }

    private suspend fun getWeather() =
        WeatherRepositoryFake().getWeather(TestData.STOCKHOLM, language = "en")

    @After
    fun tearDown() {
        database.close()
    }
}

private fun Assert<WeatherEntity>.isEqualToIgnoringId(other: WeatherEntity) {
    this.isEqualToWithGivenProperties(
        other,
        WeatherEntity::locationId,
        WeatherEntity::temperature,
        WeatherEntity::pressure,
        WeatherEntity::humidity,
        WeatherEntity::visibility,
        WeatherEntity::wind,
        WeatherEntity::clouds,
        WeatherEntity::timestamp,
        WeatherEntity::sunriseTimeMillis,
        WeatherEntity::sunsetTimeMillis,
        WeatherEntity::icon,
    )
}

private fun <T : Any> Assert<T>.isEqualToIgnoringFields(
    other: T,
    vararg properties: KProperty1<T, Any?>
) {
    all {
        other::class.members
            .filterIsInstance<KProperty1<T, Any?>>()
            .filter { it !in properties }
            .forEach { prop: KProperty1<T, Any?> ->
                transform(
                    name = appendName(prop.name, separator = "."),
                    transform = prop::get
                )
                    .isEqualTo(prop.get(other))
            }
    }
}
