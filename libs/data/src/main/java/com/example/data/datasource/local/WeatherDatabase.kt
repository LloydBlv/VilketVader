package com.example.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}