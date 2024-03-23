package com.example.data.datasource.local


internal fun getInitialLocations() = listOf(
    LocationEntity(
        id = 2657896,
        name = "Zurich",
        country = "CH",
        latitude = 47.3667f,
        longitude = 8.5500f,
        timezone = 3602,
        selected = false
    ),
    LocationEntity(
        id = 2673730,
        name = "Stockholm",
        country = "SE",
        latitude = 59.3326f,
        longitude = 18.0649f,
        timezone = 3600,
        selected = true
    ),
)