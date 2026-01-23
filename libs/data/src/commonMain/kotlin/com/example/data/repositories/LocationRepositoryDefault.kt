package com.example.data.repositories

import com.example.data.datasource.LocationDataSource
import com.example.domain.models.Location
import com.example.domain.repositories.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LocationRepositoryDefault(
    private val locationDataSource: LocationDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : LocationRepository {
    override fun observeLocations(): Flow<List<Location>> {
        return locationDataSource.observeAllLocations()
            .flowOn(ioDispatcher)
    }

    override suspend fun getSelectedLocation(): Location? = withContext(ioDispatcher) {
        locationDataSource.getSelectedLocation()
    }

    override fun observeSelectedLocation(): Flow<Location> {
        return locationDataSource.observeSelectedLocation()
            .filterNotNull()
            .flowOn(ioDispatcher)
    }

    override suspend fun updateSelectedLocation(location: Location) = withContext(ioDispatcher) {
        locationDataSource.updateSelectedLocation(location)
    }

    override suspend fun updateSelectedLocation(id: Int) = withContext(ioDispatcher) {
        locationDataSource.updateSelectedLocation(id)
    }
}
