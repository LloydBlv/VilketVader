package com.example.domain

data class Coordination(
    val latitude: Float,
    val longitude: Float,
) {
    companion object {
        val EMPTY = Coordination(
            latitude = 0f,
            longitude = 0f,
        )
    }
}