package com.example.domain.models

class NoLocationSelectedException : Exception() {
    override val message: String
        get() = "No location selected"
}
