package com.example.data.repositories

import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.LocationEntity
import com.example.data.datasource.local.toDomain
import com.example.data.datasource.local.toEntity
import com.example.domain.Location
import com.example.domain.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryDefault @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {
    override fun observeLocations(): Flow<List<Location>> {
        return locationDao.observeAllLocations()
            .map { it.map(LocationEntity::toDomain) }
    }

    override fun observeSelectedLocation(): Flow<Location?> {
        return locationDao.observeSelectedLocation()
            .map { it?.toDomain() }
    }

    override suspend fun updateSelectedLocation(location: Location) {
        locationDao.insertOrUpdateLocationWithSelection(location.toEntity())
    }
}