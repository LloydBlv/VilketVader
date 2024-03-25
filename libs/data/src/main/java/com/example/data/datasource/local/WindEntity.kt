package com.example.data.datasource.local

import androidx.room.ColumnInfo
import com.example.domain.Wind

data class WindEntity(
    @ColumnInfo("speed") val speed: Float,
    @ColumnInfo("degree") val degree: Int,
)

fun Wind.toEntity() = WindEntity(
    speed = speed,
    degree = degree,
)

internal fun WindEntity.toDomain(): Wind {
    return Wind(
        speed = speed,
        degree = degree,
    )
}
