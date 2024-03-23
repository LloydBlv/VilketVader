package com.example.domain


sealed interface WeatherResult<out T> {
    data object Loading : WeatherResult<Nothing>
    data class Success<T>(val t: T) : WeatherResult<T>
    data class Failure(val throwable: Throwable?) : WeatherResult<Nothing>
}
