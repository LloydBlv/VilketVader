package com.example.vilketvader

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.example.widget.WeatherGlanceWidget

class AppWidgetUpdater(
    private val context: Context,
) {
    suspend fun update() {
        WeatherGlanceWidget().updateAll(context)
    }
}
