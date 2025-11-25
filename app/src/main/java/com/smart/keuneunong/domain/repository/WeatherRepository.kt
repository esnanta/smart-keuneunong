package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.model.WeatherData


interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): WeatherData
}


