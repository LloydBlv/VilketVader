package com.example.data

import assertk.Assert
import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import com.example.domain.Condition
import com.example.domain.Coordination
import com.example.domain.Location
import com.example.domain.Temperature
import com.example.domain.Weather
import com.example.domain.Wind
import com.example.testing.assertTestWeather
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.SocketTimeoutException

class WeatherRepositoryTest {
    @Test
    fun `repository returns mapped data correctly`() = runTest {
        val weatherRepository = com.example.testing.WeatherRepositoryFake()
        val weather = weatherRepository.getWeather("Stockholm")
        assertThat(weather).all { assertTestWeather() }
    }
    @Test
    fun `when repository throws exception is caught`() = runTest {
        val weatherRepository = com.example.testing.WeatherRepositoryFake()
        weatherRepository.exception = SocketTimeoutException()
        assertFailure {
            weatherRepository.getWeather("Stockholm")
        }.isInstanceOf(SocketTimeoutException::class)
    }


}
