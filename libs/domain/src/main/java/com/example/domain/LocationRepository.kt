package com.example.domain

import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun observeLocations(): Flow<List<Location>>
    fun observeSelectedLocation(): Flow<Location?>

    suspend fun updateSelectedLocation(location: Location)
    suspend fun updateSelectedLocation(id: Int)
}