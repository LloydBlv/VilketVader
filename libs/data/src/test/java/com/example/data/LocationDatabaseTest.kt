package com.example.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.prop
import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.LocationEntity
import com.example.data.datasource.local.WeatherDatabase
import com.example.data.datasource.local.toEntity
import com.example.testing.TestData
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationDatabaseTest {
    private lateinit var database: WeatherDatabase
    private lateinit var dao: LocationDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.locationDao()
    }


    @Test
    fun `test inserting location result in correct retrieve`() = runTest {
        val entity = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(entity)
        val result = dao.getLocation(TestData.STOCKHOLM.id)
        assertThat(result).isEqualTo(entity)
    }
    @Test
    fun `test inserting when selected location result in correct retrieve`() = runTest {
        val entity = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(entity)
        val result = dao.getLocation(TestData.STOCKHOLM.id)
        assertThat(result).isEqualTo(entity)
    }
    @Test
    fun `when selected item gets inserted its returned correctly`() = runTest {
        val entity = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(entity)
        val result = dao.getSelectedLocation()
        assertThat(result).isEqualTo(entity)
    }
    @Test
    fun `when another selected item gets inserted its returned correctly`() = runTest {
        val stockholm = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(stockholm)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm)

        val zurich = TestData.ZURICH.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(zurich)
        assertThat(dao.getSelectedLocation()).isEqualTo(zurich)
        assertThat(dao.getLocation(TestData.STOCKHOLM.id))
            .isNotNull()
            .prop(LocationEntity::selected)
            .isFalse()
    }

    @Test
    fun `when selected item gets delete, another one becomes selected`() = runTest {
        val stockholm = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(stockholm)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm)

        val zurich = TestData.ZURICH.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(zurich)
        assertThat(dao.getSelectedLocation()).isEqualTo(zurich)

        dao.deleteLocationAndSelectNewIfNecessary(zurich)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm)
    }

    @Test
    fun `when selected item gets delete, another one becomes selected and if none is available, none is selected`() = runTest {
        val stockholm = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(stockholm)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm)

        val zurich = TestData.ZURICH.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(zurich)
        assertThat(dao.getSelectedLocation()).isEqualTo(zurich)

        dao.deleteLocationAndSelectNewIfNecessary(zurich)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm)

        dao.deleteLocationAndSelectNewIfNecessary(stockholm)
        assertThat(dao.getSelectedLocation()).isNull()
    }

    @Test
    fun `test inserting same selected location result in overriding`() = runTest {
        val stockholm = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(stockholm)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm)

        val stockholm2 = TestData.STOCKHOLM.toEntity().copy(selected = true)
        dao.insertOrUpdateLocationWithSelection(stockholm2)
        assertThat(dao.getSelectedLocation()).isEqualTo(stockholm2)
    }
    @Test
    fun `two consecutive inserts results in 2 rows afterwards`() = runTest {
        dao.insertOrUpdateLocationWithSelection(TestData.STOCKHOLM.toEntity().copy(selected = false))
        dao.insertOrUpdateLocationWithSelection(TestData.ZURICH.toEntity().copy(selected = false))

        assertThat(dao.getSelectedLocation()?.country).isEqualTo("SE")
        assertThat(dao.getAllLocations()).hasSize(2)

    }
    @After
    fun tearDown() {
        database.close()
    }
}