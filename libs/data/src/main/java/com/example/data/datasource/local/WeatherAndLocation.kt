package com.example.data.datasource.local

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherAndLocation(
    @Embedded val weather: WeatherEntity,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "id"
    )
    val location: LocationEntity
)