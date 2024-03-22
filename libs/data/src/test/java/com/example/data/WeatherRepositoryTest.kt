package com.example.data

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.example.testing.assertTestWeather
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.SocketTimeoutException

class WeatherRepositoryTest {
    @Test
    fun `repository returns mapped data correctly`() = runTest {
        val weatherRepository = com.example.testing.WeatherRepositoryFake()
        val weather = weatherRepository.getWeather("Stockholm", language = "en")
        assertThat(weather).all { assertTestWeather() }
    }

    @Test
    fun `when repository throws exception is caught`() = runTest {
        val weatherRepository = com.example.testing.WeatherRepositoryFake()
        weatherRepository.exception = SocketTimeoutException()
        assertFailure {
            val weather = weatherRepository.getWeather("Stockholm", language = "en")
        }.isInstanceOf(SocketTimeoutException::class)
    }


}
