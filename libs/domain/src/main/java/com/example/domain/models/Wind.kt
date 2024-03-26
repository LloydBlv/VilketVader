package com.example.domain.models

data class Wind(
    val speed: Float,
    val degree: Int,
) {
    companion object {
        val EMPTY = Wind(
            speed = 0f,
            degree = 0,
        )
    }
}
