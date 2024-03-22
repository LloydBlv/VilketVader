package com.example.domain

data class Location(
    val id: Int,
    val name: String,
    val coordination: Coordination,
    val timezone: Int,
    val country: String,
    val isSelected: Boolean
)