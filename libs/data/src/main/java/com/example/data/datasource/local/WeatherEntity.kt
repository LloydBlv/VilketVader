package com.example.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.Weather


@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false) val locationId: Int,

    @Embedded(prefix = "condition_") val condition: ConditionEntity?,
    @Embedded(prefix = "temp_") val temperature: TemperatureEntity,
    @ColumnInfo("pressure") val pressure: Int,
    @ColumnInfo("humidity") val humidity: Int,
    @ColumnInfo("visibility") val visibility: Long,
    @Embedded(prefix = "_wind") val wind: WindEntity,
    @ColumnInfo("clouds") val clouds: Int,
    @ColumnInfo("timestamp") val timestamp: Long,
    @Embedded(prefix = "_location") val location: LocationEntity,
    @ColumnInfo("sunrise_ms") val sunriseTimeMillis: Long,
    @ColumnInfo("sunset_ms") val sunsetTimeMillis: Long,
    @ColumnInfo("icon") val icon: String
)

fun Weather.toEntity(): WeatherEntity {
    return WeatherEntity(
        locationId = location.id,
        condition = conditions.firstOrNull()?.toEntity(),
        temperature = temperature.toEntity(),
        pressure = pressure,
        humidity = humidity,
        visibility = visibility,
        wind = wind.toEntity(),
        clouds = clouds,
        timestamp = timestamp,
        location = location.toEntity(),
        sunriseTimeMillis = sunriseTimeMillis,
        sunsetTimeMillis = sunsetTimeMillis,
        icon = icon
    )
}


fun WeatherEntity.toDomain(): Weather {
    return Weather(
        conditions = condition?.toDomain()?.let { listOf(it) }.orEmpty(),
        temperature = temperature.toDomain(),
        pressure = pressure,
        humidity = humidity,
        visibility = visibility,
        wind = wind.toDomain(),
        clouds = clouds,
        timestamp = timestamp,
        location = location.toDomain(),
        sunriseTimeMillis = sunriseTimeMillis,
        sunsetTimeMillis = sunsetTimeMillis,
        icon = icon
    )
}







