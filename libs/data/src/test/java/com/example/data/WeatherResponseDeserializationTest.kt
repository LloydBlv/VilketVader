package com.example.data

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.example.data.models.WeatherResponseDto
import org.junit.Test

class WeatherResponseDeserializationTest {
    @Test
    fun testWeatherRepository() {
        val weatherDto = com.example.testing.TestData.getTestResponse()
        assertThat(weatherDto).all {
            prop(WeatherResponseDto::clouds).transform { it?.all }.isEqualTo(0)
            prop(WeatherResponseDto::coord).all {
                transform { it?.lat }.isEqualTo(59.3326f)
                transform { it?.lon }.isEqualTo(18.0649f)
            }
            prop(WeatherResponseDto::dt).isEqualTo(1710854599)
            prop(WeatherResponseDto::id).isEqualTo(2673730)
            prop(WeatherResponseDto::main).all {
                transform { it?.temp }.isEqualTo(3.88f)
                transform { it?.feelsLike }.isEqualTo(0.73f)
                transform { it?.tempMin }.isEqualTo(1.48f)
                transform { it?.tempMax }.isEqualTo(4.54f)
                transform { it?.pressure }.isEqualTo(1020)
                transform { it?.humidity }.isEqualTo(61)
            }
            prop(WeatherResponseDto::name).isEqualTo("Stockholm")
            prop(WeatherResponseDto::sys).all {
                transform { it?.country }.isEqualTo("SE")
                transform { it?.sunriseTimeMillis }.isEqualTo(1710823898)
                transform { it?.sunsetTimeMillis }.isEqualTo(1710867553)
            }
            prop(WeatherResponseDto::timezone).isEqualTo(3600)
            prop(WeatherResponseDto::visibility).isEqualTo(10000)
            prop(WeatherResponseDto::weather).all {
                transform { it?.size }.isEqualTo(1)
                transform { it?.first()?.description }.isEqualTo("clear sky")
                transform { it?.first()?.icon }.isEqualTo("01d")
                transform { it?.first()?.id }.isEqualTo(800)
                transform { it?.first()?.main }.isEqualTo("Clear")
            }
            prop(WeatherResponseDto::wind).all {
                transform { it?.deg }.isEqualTo(230)
                transform { it?.speed }.isEqualTo(3.6f)
            }
        }
    }
}