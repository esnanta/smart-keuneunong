package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.model.WeatherData
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherData {
        // Dummy data
        return WeatherData(
            temperature = 28,
            condition = "Cerah Berawan",
            humidity = 75,
            windSpeed = 10,
            weatherIcon = "ic_clear",
            feelsLike = 30,
            location = "Bandung",
            date = System.currentTimeMillis()
        )
    }
}

