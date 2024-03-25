package com.example.data

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.example.testing.TestData
import com.example.testing.WeatherRepositoryFake
import com.example.testing.assertTestWeather
import java.net.SocketTimeoutException
import kotlinx.coroutines.test.runTest
import org.junit.Test

class WeatherRepositoryTest {
    @Test
    fun `repository returns mapped data correctly`() = runTest {
        val weatherRepository = WeatherRepositoryFake()
        val weather = getWeather(weatherRepository)
        assertThat(weather).all { assertTestWeather() }
    }

    @Test
    fun `when repository throws exception is caught`() = runTest {
        val weatherRepository = WeatherRepositoryFake()
        weatherRepository.exception = SocketTimeoutException()
        assertFailure {
            val weather = getWeather(weatherRepository)
        }.isInstanceOf(SocketTimeoutException::class)
    }

    private suspend fun getWeather(weatherRepository: WeatherRepositoryFake) =
        weatherRepository.getWeather(TestData.STOCKHOLM, language = "en")
}
