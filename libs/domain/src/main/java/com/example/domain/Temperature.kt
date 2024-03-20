package com.example.domain

data class Temperature(
    val current: Float,
    val feelsLike: Float,
    val min: Float,
    val max: Float,
    val unit: String,
)
