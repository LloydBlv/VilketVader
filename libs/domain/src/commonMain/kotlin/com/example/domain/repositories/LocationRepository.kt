package com.example.domain.repositories

import com.example.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun observeLocations(): Flow<List<Location>>
    fun observeSelectedLocation(): Flow<Location>
    suspend fun getSelectedLocation(): Location?

    suspend fun updateSelectedLocation(location: Location)
    suspend fun updateSelectedLocation(id: Int)
}
