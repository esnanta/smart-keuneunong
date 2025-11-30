package com.smart.keuneunong.di

import com.smart.keuneunong.data.repository.RainfallRepositoryImpl
import com.smart.keuneunong.domain.repository.RainfallRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Module untuk Rainfall Feature
 * Menyediakan dependency injection untuk repository
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RainfallModule {

    @Binds
    @Singleton
    abstract fun bindRainfallRepository(
        rainfallRepositoryImpl: RainfallRepositoryImpl
    ): RainfallRepository
}

