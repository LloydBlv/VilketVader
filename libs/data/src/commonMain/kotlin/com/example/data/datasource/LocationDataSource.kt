package com.example.data.datasource

import com.example.domain.models.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    fun observeAllLocations(): Flow<List<Location>>
    suspend fun getSelectedLocation(): Location?
    fun observeSelectedLocation(): Flow<Location?>
    suspend fun updateSelectedLocation(location: Location)
    suspend fun updateSelectedLocation(id: Int)
}
