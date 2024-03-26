package com.example.wear.di

import android.content.Context
import com.example.domain.usecases.GetSelectedWeatherUseCase
import com.example.domain.usecases.RefreshSelectedWeatherUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WearEntryPoint {
    fun getSelectedWeatherUseCase(): GetSelectedWeatherUseCase
    fun refreshWeatherUseCase(): RefreshSelectedWeatherUseCase
}

internal fun getSelectedWeatherUseCase(context: Context): GetSelectedWeatherUseCase {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context,
        WearEntryPoint::class.java,
    )
    return hiltEntryPoint.getSelectedWeatherUseCase()
}
