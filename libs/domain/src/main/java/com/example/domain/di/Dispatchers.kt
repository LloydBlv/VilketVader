package com.example.domain.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ReadDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class WriteDispatcher
