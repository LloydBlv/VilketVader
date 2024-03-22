package com.example.data.datasource.local

import androidx.room.ColumnInfo
import com.example.domain.Coordination
import com.example.domain.Location

data class LocationEntity (
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("country") val country: String,
    @ColumnInfo("latitude") val latitude: Float,
    @ColumnInfo("longitude") val longitude: Float,
    @ColumnInfo("timezone") val timezone: Int,
)

fun Location.toEntity() = LocationEntity(
    id = id,
    name = name,
    country = country,
    latitude = coordination.latitude,
    longitude = coordination.longitude,
    timezone = timezone
)

internal fun LocationEntity.toDomain(): Location {
    return Location(
        id = id,
        name = name,
        country = country,
        coordination = Coordination(latitude, longitude),
        timezone = timezone
    )
}
