package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather where locationId = :id")
    suspend fun getWeather(id: Int): WeatherEntity
    @Query("SELECT * FROM weather")
    suspend fun getAllWeathers(): List<WeatherEntity>

    @Query("SELECT * FROM weather where locationId = :id")
    fun observeLocation(id: Int): Flow<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Delete
    suspend fun deleteWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather")
    suspend fun deleteAll()
}