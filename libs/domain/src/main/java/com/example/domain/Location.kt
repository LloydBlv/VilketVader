package com.example.domain

data class Location(
    val id: Int,
    val name: String,
    val coordination: Coordination,
    val timezone: Int,
    val country: String,
    val isSelected: Boolean
) {
    companion object {
        val EMPTY = Location(
            id = 0,
            name = "",
            coordination = Coordination.EMPTY,
            timezone = 0,
            country = "",
            isSelected = false
        )
    }
}
