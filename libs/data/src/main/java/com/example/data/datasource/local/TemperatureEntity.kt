package com.example.data.datasource.local

import androidx.room.ColumnInfo
import com.example.domain.models.Temperature

data class TemperatureEntity(
    @ColumnInfo("current") val current: Float,
    @ColumnInfo("feels_like") val feelsLike: Float,
    @ColumnInfo("min") val min: Float,
    @ColumnInfo("max") val max: Float,
)

fun Temperature.toEntity() = TemperatureEntity(
    current = current,
    feelsLike = feelsLike,
    min = min,
    max = max,
)

internal fun TemperatureEntity.toDomain(): Temperature {
    return Temperature(
        current = current,
        min = min,
        max = max,
        feelsLike = feelsLike,
    )
}
