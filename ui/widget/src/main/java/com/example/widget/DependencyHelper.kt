package com.example.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.domain.usecases.ObserveSelectedWeatherUseCase
import com.example.domain.usecases.RefreshSelectedWeatherUseCase
import dagger.hilt.android.EntryPointAccessors

internal fun getGlanceAppWidgetManager(context: Context): GlanceAppWidgetManager {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context,
        WidgetEntryPoint::class.java,
    )
    return hiltEntryPoint.glanceAppWidgetManager()
}
internal fun getObserveLocationUseCase(context: Context): ObserveSelectedWeatherUseCase {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context,
        WidgetEntryPoint::class.java,
    )
    return hiltEntryPoint.observeLocationUseCase()
}
internal fun getRefreshWeather(context: Context): RefreshSelectedWeatherUseCase {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context,
        WidgetEntryPoint::class.java,
    )
    return hiltEntryPoint.refreshWeatherUseCase()
}
