package com.example.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.Coordination
import com.example.domain.Location

@Entity(tableName = "location")
data class LocationEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("country") val country: String,
    @ColumnInfo("latitude") val latitude: Float,
    @ColumnInfo("longitude") val longitude: Float,
    @ColumnInfo("timezone") val timezone: Int,
    @ColumnInfo("selected") val selected: Boolean,
)

fun Location.toEntity() = LocationEntity(
    id = id,
    name = name,
    country = country,
    latitude = coordination.latitude,
    longitude = coordination.longitude,
    timezone = timezone,
    selected = isSelected
)

internal fun LocationEntity.toDomain(): Location {
    return Location(
        id = id,
        name = name,
        country = country,
        coordination = Coordination(latitude, longitude),
        timezone = timezone,
        isSelected = selected
    )
}
