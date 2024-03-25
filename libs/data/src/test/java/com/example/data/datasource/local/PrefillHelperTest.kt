package com.example.data.datasource.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PrefillHelperTest {
    private lateinit var database: WeatherDatabase
    private lateinit var locationDao: LocationDao
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java,
        )
            .allowMainThreadQueries()
            .build()
        locationDao = database.locationDao()
        weatherDao = database.weatherDao()
    }

    @Test
    fun `prefill with first item as selected, stores correctly`() = runTest {
        val prefillHelper = PrefillHelper(dagger.Lazy<LocationDao> { locationDao })
        val zurich = LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = true,
        )
        val stockholm = LocationEntity(
            id = 2673730,
            name = "Stockholm",
            country = "SE",
            latitude = 59.3326f,
            longitude = 18.0649f,
            timezone = 3600,
            selected = false,
        )
        val locations = listOf(zurich, stockholm)
        prefillHelper.prefill(locations)
        assertThat(locationDao.getAllLocations()).hasSize(2)
        assertThat(locationDao.getSelectedLocation()).isEqualTo(zurich)
    }

    @Test
    fun `prefill with no items as selected, stores correctly`() = runTest {
        val prefillHelper = PrefillHelper(dagger.Lazy<LocationDao> { locationDao })
        val zurich = LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = false,
        )
        val stockholm = LocationEntity(
            id = 2673730,
            name = "Stockholm",
            country = "SE",
            latitude = 59.3326f,
            longitude = 18.0649f,
            timezone = 3600,
            selected = false,
        )
        val locations = listOf(zurich, stockholm)
        prefillHelper.prefill(locations)
        assertThat(locationDao.getAllLocations().filter { it.selected }).hasSize(1)
        assertThat(locationDao.getSelectedLocation()).isNotNull()
            .all {
                prop(LocationEntity::selected).isTrue()
                prop(LocationEntity::name).isEqualTo(zurich.name)
            }
    }

    @Test
    fun `prefill with second item as selected, stores correctly`() = runTest {
        val prefillHelper = PrefillHelper(dagger.Lazy<LocationDao> { locationDao })
        val zurich = LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = false,
        )
        val stockholm = LocationEntity(
            id = 2673730,
            name = "Stockholm",
            country = "SE",
            latitude = 59.3326f,
            longitude = 18.0649f,
            timezone = 3600,
            selected = true,
        )
        val locations = listOf(zurich, stockholm)
        prefillHelper.prefill(locations)
        assertThat(locationDao.getAllLocations().filter { it.selected }).hasSize(1)
        assertThat(locationDao.getSelectedLocation()).isNotNull()
            .all {
                prop(LocationEntity::selected).isTrue()
                prop(LocationEntity::name).isEqualTo(stockholm.name)
            }
    }

    @Test
    fun `prefill with both items as selected, stores correctly`() = runTest {
        val prefillHelper = PrefillHelper(dagger.Lazy<LocationDao> { locationDao })
        val zurich = LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = true,
        )
        val stockholm = LocationEntity(
            id = 2673730,
            name = "Stockholm",
            country = "SE",
            latitude = 59.3326f,
            longitude = 18.0649f,
            timezone = 3600,
            selected = true,
        )
        val locations = listOf(zurich, stockholm)
        prefillHelper.prefill(locations)
        assertThat(locationDao.getAllLocations().filter { it.selected }).hasSize(1)
        assertThat(locationDao.getSelectedLocation()).isNotNull()
            .all {
                prop(LocationEntity::selected).isTrue()
                prop(LocationEntity::name).isEqualTo(stockholm.name)
            }
    }

    @Test
    fun `prefill with no item different order as selected, stores correctly`() = runTest {
        val prefillHelper = PrefillHelper(dagger.Lazy<LocationDao> { locationDao })
        val zurich = LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = false,
        )
        val stockholm = LocationEntity(
            id = 2673730,
            name = "Stockholm",
            country = "SE",
            latitude = 59.3326f,
            longitude = 18.0649f,
            timezone = 3600,
            selected = false,
        )
        val locations = listOf(stockholm, zurich)
        prefillHelper.prefill(locations)
        assertThat(locationDao.getAllLocations().filter { it.selected }).hasSize(1)
        assertThat(locationDao.getSelectedLocation()).isNotNull()
            .all {
                prop(LocationEntity::selected).isTrue()
                prop(LocationEntity::name).isEqualTo(stockholm.name)
            }
    }

    @Test
    fun `prefill one location should maintain correct selected state`() = runTest {
        val prefillHelper = PrefillHelper(dagger.Lazy<LocationDao> { locationDao })
        val zurich = LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = false,
        )
        val locations = listOf(zurich)
        prefillHelper.prefill(locations)
        assertThat(locationDao.getAllLocations()).hasSize(1)
        assertThat(locationDao.getSelectedLocation()).isNotNull()
            .all {
                prop(LocationEntity::selected).isTrue()
                prop(LocationEntity::name).isEqualTo(zurich.name)
            }
    }

    @After
    fun tearDown() {
        database.close()
    }
}
