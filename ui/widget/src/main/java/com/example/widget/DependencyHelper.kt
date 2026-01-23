package com.example.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.domain.usecases.ObserveSelectedWeatherUseCase
import com.example.domain.usecases.RefreshSelectedWeatherUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object WidgetDependencies : KoinComponent {
    val glanceAppWidgetManager: GlanceAppWidgetManager by inject()
    val observeSelectedWeatherUseCase: ObserveSelectedWeatherUseCase by inject()
    val refreshSelectedWeatherUseCase: RefreshSelectedWeatherUseCase by inject()
}

internal fun getGlanceAppWidgetManager(context: Context): GlanceAppWidgetManager {
    return WidgetDependencies.glanceAppWidgetManager
}

internal fun getObserveLocationUseCase(context: Context): ObserveSelectedWeatherUseCase {
    return WidgetDependencies.observeSelectedWeatherUseCase
}

internal fun getRefreshWeather(context: Context): RefreshSelectedWeatherUseCase {
    return WidgetDependencies.refreshSelectedWeatherUseCase
}
