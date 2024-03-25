package com.example.vilketvader

import com.example.domain.DateFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateFormatterDefault : DateFormatter {
    override fun formatWeekDayAndTime(timestamp: Long): String {
        val instant = Instant.fromEpochSeconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, localDateTime.year)
            set(Calendar.MONTH, localDateTime.monthNumber - 1) // Calendar.MONTH is 0-based
            set(Calendar.DAY_OF_MONTH, localDateTime.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, localDateTime.hour)
            set(Calendar.MINUTE, localDateTime.minute)
        }
        val formatter = SimpleDateFormat("EEEE, HH:mm", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}
