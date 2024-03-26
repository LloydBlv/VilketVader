package com.example.data.repositories

import com.example.data.datasource.local.LocationDao
import com.example.data.datasource.local.LocationEntity
import com.example.data.datasource.local.toDomain
import com.example.data.datasource.local.toEntity
import com.example.domain.di.IoDispatcher
import com.example.domain.models.Location
import com.example.domain.repositories.LocationRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocationRepositoryDefault @Inject constructor(
    private val locationDao: LocationDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : LocationRepository {
    override fun observeLocations(): Flow<List<Location>> {
        return locationDao.observeAllLocations()
            .map { it.map(LocationEntity::toDomain) }
            .flowOn(ioDispatcher)
    }

    override suspend fun getSelectedLocation(): Location? = withContext(ioDispatcher) {
        locationDao.getSelectedLocation()?.toDomain()
    }

    override fun observeSelectedLocation(): Flow<Location> {
        return locationDao.observeSelectedLocation()
            .filterNotNull()
            .map(LocationEntity::toDomain)
            .flowOn(ioDispatcher)
    }

    override suspend fun updateSelectedLocation(location: Location) = withContext(ioDispatcher) {
        locationDao.insertOrUpdateLocationWithSelection(location.toEntity())
    }

    override suspend fun updateSelectedLocation(id: Int) = withContext(ioDispatcher) {
        locationDao.updateSelectedLocation(id)
    }
}
