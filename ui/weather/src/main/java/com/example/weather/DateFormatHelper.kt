package com.example.weather

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.domain.DateFormatter

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}

