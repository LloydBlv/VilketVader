package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    @Query("UPDATE location SET selected = 0")
    suspend fun deselectAllLocations()


    @Query("SELECT * FROM location WHERE selected = 1")
    fun getSelectedLocation(): LocationEntity?

    @Query("SELECT COUNT(*) FROM location")
    suspend fun getLocationCount(): Int

    @Query("SELECT * FROM location where id = :id")
    suspend fun getLocation(id: Int): LocationEntity?

    @Query("SELECT * FROM location")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM location")
    fun observeAllLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM location where id = :id")
    fun observeLocation(id: Int): Flow<LocationEntity>

    @Query("SELECT * FROM location where selected = 1")
    fun observeSelectedLocation(): Flow<LocationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Query("DELETE FROM location")
    suspend fun deleteAll()

    @Query("SELECT * FROM location ORDER BY id LIMIT 1")
    suspend fun getAnyLocation(): LocationEntity?
    @Transaction
    suspend fun deleteLocationAndSelectNewIfNecessary(locationEntity: LocationEntity) {
        deleteLocation(locationEntity)
        if (locationEntity.selected) {
            val anyLocation = getAnyLocation()
            if (anyLocation != null) {
                insertLocation(anyLocation.copy(selected = true))
            }
        }
    }


    @Transaction
    suspend fun insertOrUpdateLocationWithSelection(locationEntity: LocationEntity) {
        if (locationEntity.selected) {
            deselectAllLocations()
            insertLocation(locationEntity)
            return
        }
        if (getLocationCount() == 0){
            insertLocation(locationEntity.copy(selected = true))
            return
        }
        if (getLocationCount() == 1) {
            val existingLocation = getAnyLocation()!!
            insertLocation(existingLocation.copy(selected = true))
            return
        }
        insertLocation(locationEntity)
    }
}