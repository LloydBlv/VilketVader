package com.example.testing

import com.example.domain.Location
import com.example.domain.LocationRepository
import kotlinx.coroutines.flow.flowOf

class FakeLocationRepository(val locations: MutableList<Location>) : LocationRepository {
    var exception: Throwable? = null
    override fun observeLocations() = flowOf(locations)
    override fun observeSelectedLocation() = flowOf(locations.find { it.isSelected })
    override suspend fun updateSelectedLocation(location: Location) {
        if (exception != null) {
            throw exception!!
        }
    }

    override suspend fun updateSelectedLocation(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedLocation(): Location? {
        TODO("Not yet implemented")
    }
}