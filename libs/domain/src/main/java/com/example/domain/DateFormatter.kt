package com.example.domain

interface DateFormatter {
    fun formatWeekDayAndTime(timestamp: Long): String
}