package com.example.testing

import com.example.domain.Location
import com.example.domain.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocationRepository(
    val locations: MutableList<Location> = buildList {
        add(TestData.STOCKHOLM.copy(isSelected = true))
        add(TestData.ZURICH.copy(isSelected = false))
    }.toMutableList()
) : LocationRepository {
    var exception: Throwable? = null
    override fun observeLocations() = flowOf(locations)
    override fun observeSelectedLocation(): Flow<Location> =
        flowOf(locations.find { it.isSelected }!!)

    override suspend fun updateSelectedLocation(location: Location) {
        if (exception != null) {
            throw exception!!
        }
    }

    override suspend fun updateSelectedLocation(id: Int) {
        val newLocations = mutableListOf<Location>()
        locations.forEach {
            newLocations.add(it.copy(isSelected = it.id == id))
        }
        locations.clear()
        locations.addAll(newLocations)
    }

    override suspend fun getSelectedLocation(): Location? {
        return locations.find { it.isSelected }
    }
}
