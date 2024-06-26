package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather where location_id = :locationId")
    @Transaction
    suspend fun getWeather(locationId: Int): WeatherAndLocation?

    @Query("SELECT * FROM weather")
    @Transaction
    suspend fun getAllWeathers(): List<WeatherAndLocation>

    @Query("SELECT * FROM weather")
    suspend fun getOnlyAllWeathers(): List<WeatherEntity>

    @Query("SELECT * FROM weather where location_id = :id")
    @Transaction
    fun observeWeather(id: Int): Flow<WeatherAndLocation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("DELETE FROM weather where location_id = :locationId")
    suspend fun deleteWeather(locationId: Int)

    @Query("DELETE FROM weather")
    suspend fun deleteAll()
}
