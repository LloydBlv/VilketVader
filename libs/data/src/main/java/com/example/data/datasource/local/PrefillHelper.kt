package com.example.data.datasource.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefillHelper @Inject constructor(
    private val locationsDao: dagger.Lazy<LocationDao>
) {
    suspend fun prefill(locations: List<LocationEntity>) {
        val dao = locationsDao.get()
        locations.forEach { location ->
            dao.insertOrUpdateLocationWithSelection(location)
        }
    }
}
