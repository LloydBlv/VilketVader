package com.example.data.di

import com.example.data.datasource.local.LocalDataSource
import com.example.data.datasource.local.LocalDataSourceDefault
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun localDataSource(default: LocalDataSourceDefault): LocalDataSource
}