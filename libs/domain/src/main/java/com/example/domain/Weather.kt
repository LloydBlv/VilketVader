package com.example.domain

data class Weather(
    val location: Location,
    val temperature: Temperature,
    val conditions: List<Condition>,
    val wind: Wind,
    val pressure: Int,
    val humidity: Int,
    val visibility: Long,
    val clouds: Int,
    val timestamp: Long,
    val sunriseTimeMillis: Long,
    val sunsetTimeMillis: Long,
    val icon: String = ""
) {
    fun isRainy() = conditions.any { it.type == Condition.Type.RAIN }
}

