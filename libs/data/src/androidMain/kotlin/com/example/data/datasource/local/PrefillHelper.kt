package com.example.data.datasource.local

class PrefillHelper(
    private val locationDaoProvider: () -> LocationDao,
) {
    suspend fun prefill(locations: List<LocationEntity>) {
        val dao = locationDaoProvider()
        locations.forEach { location ->
            dao.insertOrUpdateLocationWithSelection(location)
        }
    }
}
