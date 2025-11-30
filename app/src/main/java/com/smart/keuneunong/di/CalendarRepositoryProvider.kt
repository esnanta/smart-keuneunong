package com.smart.keuneunong.di

import com.smart.keuneunong.data.network.WeatherApi
import com.smart.keuneunong.data.preferences.LocationPreferencesManager
import com.smart.keuneunong.data.repository.CalendarRepositoryImpl
import com.smart.keuneunong.domain.repository.CalendarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalendarRepositoryProvider {

    @Provides
    @Singleton
    fun provideCalendarRepository(
        weatherApi: WeatherApi,
        locationPreferencesManager: LocationPreferencesManager
    ): CalendarRepository {
        return CalendarRepositoryImpl(weatherApi, locationPreferencesManager)
    }
}
