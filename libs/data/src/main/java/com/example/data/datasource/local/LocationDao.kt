package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {
     @Query("SELECT * FROM location where id = :id")
     suspend fun getLocation(id: Int): LocationEntity?
     @Query("SELECT * FROM location")
     suspend fun getAllLocations(): List<LocationEntity>

     @Query("SELECT * FROM location where id = :id")
     fun observeLocation(id: Int): Flow<LocationEntity>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertLocation(location: LocationEntity)

     @Delete
     suspend fun deleteLocation(location: LocationEntity)

     @Query("DELETE FROM location")
     suspend fun deleteAll()
}