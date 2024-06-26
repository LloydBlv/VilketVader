package com.example.data.di

import com.example.domain.di.DefaultDispatcher
import com.example.domain.di.IoDispatcher
import com.example.domain.di.ReadDispatcher
import com.example.domain.di.WriteDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @ReadDispatcher
    @Provides
    fun providesReadDispatcher(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1)

    @WriteDispatcher
    @Provides
    fun providesWriteDispatcher(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(4)
}
