package com.example.domain

data class Condition(
    val name: String,
    val description: String,
    val type: Type
) {
    enum class Type {
        UNKNOWN,
        THUNDERSTORM,
        SNOW,
        RAIN,
        DRIZZLE,
        CLOUDS,
        ATMOSPHERE,
        CLEAR;

        companion object {
            fun from(code: Int): Type {
                return when (code) {
                    0 -> UNKNOWN
                    in 200..299 -> THUNDERSTORM
                    in 300..399 -> DRIZZLE
                    in 500..599 -> RAIN
                    in 600..699 -> SNOW
                    in 700..799 -> ATMOSPHERE
                    800 -> CLEAR
                    else -> CLOUDS
                }
            }
        }
    }
}


