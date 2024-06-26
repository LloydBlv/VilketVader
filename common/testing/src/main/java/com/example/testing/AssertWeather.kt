package com.example.testing

import assertk.Assert
import assertk.all
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.example.domain.models.Condition
import com.example.domain.models.Coordination
import com.example.domain.models.Location
import com.example.domain.models.Temperature
import com.example.domain.models.Weather
import com.example.domain.models.Wind

fun Assert<Weather>.assertTestWeather() {
    assertLocation()
    assertConditions()
    assertTemperature()
    assertWind()
    prop(Weather::pressure).isEqualTo(1020)
    prop(Weather::humidity).isEqualTo(61)
    prop(Weather::visibility).isEqualTo(10000)
    prop(Weather::clouds).isEqualTo(0)
    prop(Weather::sunriseTimeMillis).isEqualTo(1710823898)
    prop(Weather::sunsetTimeMillis).isEqualTo(1710867553)
    prop(Weather::timestamp).isEqualTo(1710854599)
}

private fun Assert<Weather>.assertWind() {
    prop(Weather::wind).all {
        prop(Wind::speed).isEqualTo(3.6f)
        prop(Wind::degree).isEqualTo(230)
    }
}

private fun Assert<Weather>.assertTemperature() {
    prop(Weather::temperature).all {
        prop(Temperature::current).isEqualTo(3.88f)
        prop(Temperature::feelsLike).isEqualTo(0.73f)
        prop(Temperature::min).isEqualTo(1.48f)
        prop(Temperature::max).isEqualTo(4.54f)
    }
}

private fun Assert<Weather>.assertConditions() {
    prop(Weather::conditions).transform { it.first() }.all {
        prop(Condition::name).isEqualTo("Clear")
        prop(Condition::description).isEqualTo("clear sky")
    }
}

private fun Assert<Weather>.assertLocation() {
    prop(Weather::location).all {
        prop(Location::id).isEqualTo(2673730)
        prop(Location::name).isEqualTo("Stockholm")
        prop(Location::country).isEqualTo("SE")
        prop(Location::timezone).isEqualTo(3600)
        prop(Location::coordination).all {
            prop(Coordination::latitude).isEqualTo(59.3326f)
            prop(Coordination::longitude).isEqualTo(18.0649f)
        }
    }
}
