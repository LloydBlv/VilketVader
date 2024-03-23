package com.example.domain


sealed interface WeatherResult<out T> {
    data object Loading : WeatherResult<Nothing>
    data class Success<T>(val data: T) : WeatherResult<T>
    data class Failure(val throwable: Throwable?) : WeatherResult<Nothing>

    val isSuccess: Boolean get() = this is Success

    /**
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    val isFailure: Boolean get() = this is Failure

    val isLoading: Boolean get() = this is Loading
    fun getOrNull(): T? =
        when {
            this is Success -> this.data
            else -> null
        }

    /**
     * Returns the encapsulated [Throwable] exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     * This function is a shorthand for `fold(onSuccess = { null }, onFailure = { it })` (see [fold]).
     */
    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> this.throwable
            else -> null
        }

    companion object {

        fun <T> loading() = Loading
        fun <T> success(value: T): WeatherResult<T> =
            Success(value)

        /**
         * Returns an instance that encapsulates the given [Throwable] [exception] as failure.
         */
        @JvmName("failure")
        fun <T> failure(exception: Throwable): WeatherResult<T> =
            Failure(exception)
    }
}
