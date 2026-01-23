package com.example.data.datasource.local

import com.example.data.datasource.LocationDataSource
import com.example.domain.models.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationDataSourceDefault(
    private val locationDao: LocationDao,
) : LocationDataSource {
    override fun observeAllLocations(): Flow<List<Location>> {
        return locationDao.observeAllLocations()
            .map { it.map(LocationEntity::toDomain) }
    }

    override suspend fun getSelectedLocation(): Location? {
        return locationDao.getSelectedLocation()?.toDomain()
    }

    override fun observeSelectedLocation(): Flow<Location?> {
        return locationDao.observeSelectedLocation()
            .map { it?.toDomain() }
    }

    override suspend fun updateSelectedLocation(location: Location) {
        locationDao.insertOrUpdateLocationWithSelection(location.toEntity())
    }

    override suspend fun updateSelectedLocation(id: Int) {
        locationDao.updateSelectedLocation(id)
    }
}
