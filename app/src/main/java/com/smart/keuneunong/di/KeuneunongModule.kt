package com.smart.keuneunong.di

import com.smart.keuneunong.domain.repository.RepositoryKeuneunong
import com.smart.keuneunong.data.repository.KeuneunongRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeuneunongModule {
    @Provides
    @Singleton
    fun provideRepositoryKeuneunong(): RepositoryKeuneunong = KeuneunongRepositoryImpl()
}

