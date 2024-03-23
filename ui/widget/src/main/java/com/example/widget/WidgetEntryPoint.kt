package com.example.widget

import androidx.glance.appwidget.GlanceAppWidgetManager
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.RefreshSelectedWeatherUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun glanceAppWidgetManager(): GlanceAppWidgetManager
    fun observeLocationUseCase(): ObserveSelectedWeatherUseCase
    fun refreshWeatherUseCase(): RefreshSelectedWeatherUseCase
}