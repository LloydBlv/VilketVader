package com.example.domain

data class Weather(
    val condition: Condition,
    val temperature: Temperature,
    val pressure: Int,
    val humidity: Int,
    val visibility: Long,
    val wind: Wind,
    val clouds: Int,
    val timestamp: Long,
    val location: Location,
    val sunrise: Long,
    val sunset: Long
)

