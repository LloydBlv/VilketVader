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
    val icon: Icon = Icon("")
) {
    fun isRainy() = conditions.any { it.type == Condition.Type.RAIN }
    companion object {
        val EMPTY = Weather(
            location = Location.EMPTY,
            temperature = Temperature.EMPTY,
            conditions = emptyList(),
            wind = Wind.EMPTY,
            pressure = 0,
            humidity = 0,
            visibility = 0,
            clouds = 0,
            timestamp = 0,
            sunriseTimeMillis = 0,
            sunsetTimeMillis = 0,
        )
    }
}

