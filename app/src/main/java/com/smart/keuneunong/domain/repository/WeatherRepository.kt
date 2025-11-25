package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.ui.weather.WeatherData

interface WeatherRepository {
    suspend fun getWeatherData(): WeatherData
}

