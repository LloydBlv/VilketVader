package com.example.data.datasource.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrefillDatabase @Inject constructor(
    private val locationsDao: dagger.Lazy<LocationDao>
): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            initialLocations.forEach { location ->
                locationsDao.get().insertOrUpdateLocationWithSelection(location)
            }
        }
    }

    private val initialLocations = listOf(
        LocationEntity(
            id = 2673730,
            name = "Stockholm",
            country = "SE",
            latitude = 59.3326f,
            longitude = 18.0649f,
            timezone = 3600,
            selected = true
        ),
        LocationEntity(
            id = 2657896,
            name = "Zurich",
            country = "CH",
            latitude = 47.3667f,
            longitude = 8.5500f,
            timezone = 3600,
            selected = false
        )
    )

}