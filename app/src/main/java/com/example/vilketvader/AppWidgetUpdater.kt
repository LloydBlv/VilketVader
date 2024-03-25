package com.example.vilketvader

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.example.widget.WeatherGlanceWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppWidgetUpdater @Inject constructor(
    @ApplicationContext val context: Context,
) {
    suspend fun update() {
        WeatherGlanceWidget().updateAll(context)
    }
}
