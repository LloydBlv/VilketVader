package com.example.weather

import androidx.compose.runtime.staticCompositionLocalOf

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}

interface DateFormatter {
    fun formatWeekDayAndTime(timestamp: Long): String
}