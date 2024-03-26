package com.example.data.di

import com.example.data.repositories.LocationRepositoryDefault
import com.example.domain.repositories.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {
    @Binds
    fun bindLocationRepository(default: LocationRepositoryDefault): LocationRepository
}
