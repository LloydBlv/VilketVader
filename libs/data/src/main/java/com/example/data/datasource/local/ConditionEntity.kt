package com.example.data.datasource.local

import androidx.room.ColumnInfo
import com.example.domain.Condition

data class ConditionEntity(
    @ColumnInfo("type") val type: Int,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("name") val name: String,
)

fun Condition.toEntity() = ConditionEntity(
    type = type.ordinal,
    description = description,
    name = name,
)

internal fun ConditionEntity.toDomain(): Condition {
    return Condition(
        type = Condition.Type.entries[type],
        description = description,
        name = name,
    )
}
