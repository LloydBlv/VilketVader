package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.example.data.datasource.local.WeatherDatabase
import com.example.data.repositories.LocationRepositoryDefault
import com.example.domain.Location
import com.example.domain.LocationRepository
import com.example.testing.TestData
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationRepositoryTest {

    private lateinit var repository: LocationRepository

    @Before
    fun setup() {
        val database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        repository = LocationRepositoryDefault(database.locationDao())
    }

    @Test
    fun `when database is empty, no location is observed`() = runTest {
        repository.observeLocations().test {
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when database is empty, no selected location is observed`() = runTest {
        repository.observeSelectedLocation().test {
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when location is inserted, it is observed`() = runTest {
        val location = TestData.STOCKHOLM
        repository.updateSelectedLocation(location)
        repository.observeLocations().test {
            assertThat(awaitItem()).isEqualTo(listOf(location.copy(isSelected = true)))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when locations are empty, first item will be converted to selected`() = runTest {
        val location = TestData.STOCKHOLM
        repository.updateSelectedLocation(location)
        repository.observeSelectedLocation().test {
            assertThat(awaitItem()).isNotNull().isEqualTo(location.copy(isSelected = true))
            ensureAllEventsConsumed()
        }
    }
    @Test
    fun `when locations are empty, first item will be selected`() = runTest {
        val location = TestData.STOCKHOLM.copy(isSelected = true)
        repository.updateSelectedLocation(location)
        repository.observeSelectedLocation().test {
            assertThat(awaitItem()).isNotNull().isEqualTo(location)
            ensureAllEventsConsumed()
        }
    }
    @Test
    fun `when theres one location, its not possible to mark it unselected`() = runTest {
        val location = TestData.STOCKHOLM.copy(isSelected = true)
        repository.updateSelectedLocation(location)
        repository.observeSelectedLocation().test {
            assertThat(awaitItem()).isNotNull().prop(Location::isSelected).isTrue()
            repository.updateSelectedLocation(location.copy(isSelected = false))
            assertThat(awaitItem()).isNotNull().prop(Location::isSelected).isTrue()
            ensureAllEventsConsumed()
        }
    }

}