package com.example.domain

class NoLocationSelectedException : Exception() {
    override val message: String
        get() = "No location selected"
}