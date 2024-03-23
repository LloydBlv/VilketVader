package com.example.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.Icon
import com.example.domain.Weather


@Entity(
    tableName = "weather",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ],
    indices = [Index(value = ["location_id"], unique = true)]
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo("location_id") val locationId: Int,

    @Embedded(prefix = "condition_") val condition: ConditionEntity?,
    @Embedded(prefix = "temp_") val temperature: TemperatureEntity,
    @ColumnInfo("pressure") val pressure: Int,
    @ColumnInfo("humidity") val humidity: Int,
    @ColumnInfo("visibility") val visibility: Long,
    @Embedded(prefix = "_wind") val wind: WindEntity,
    @ColumnInfo("clouds") val clouds: Int,
    @ColumnInfo("timestamp") val timestamp: Long,
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
        sunriseTimeMillis = sunriseTimeMillis,
        sunsetTimeMillis = sunsetTimeMillis,
        icon = icon.type,
    )
}


fun WeatherAndLocation.toDomain(): Weather {
    with(weather) {
        return Weather(
            conditions = listOfNotNull(condition?.toDomain()),
            temperature = temperature.toDomain(),
            pressure = pressure,
            humidity = humidity,
            visibility = visibility,
            wind = wind.toDomain(),
            clouds = clouds,
            timestamp = timestamp,
            sunriseTimeMillis = sunriseTimeMillis,
            sunsetTimeMillis = sunsetTimeMillis,
            icon = Icon(type = icon),
            location = location.toDomain()
        )
    }
}







