package com.example.domain

data class Temperature(
    val current: Float,
    val feelsLike: Float,
    val min: Float,
    val max: Float,
) {
    companion object {
        val EMPTY = Temperature(
            current = 0f,
            feelsLike = 0f,
            min = 0f,
            max = 0f,
        )
    }
}
