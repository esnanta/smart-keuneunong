package com.smart.keuneunong.data.repository

import com.smart.keuneunong.domain.repository.WeatherRepository
import com.smart.keuneunong.ui.weather.DailyForecast
import com.smart.keuneunong.ui.weather.HourlyForecast
import com.smart.keuneunong.ui.weather.WeatherData
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override suspend fun getWeatherData(): WeatherData {
        delay(1000) // Simulate network delay
        return WeatherData(
            location = "Banda Aceh",
            temperature = 29,
            condition = "Cerah Berawan",
            weatherIcon = "🌤️",
            keuneunongContext = "Saat ini: Keuneunong Ròt (Musim Tanam)",
            keuneunongDescription = "Cuaca cerah berawan sangat baik untuk memulai pengolahan sawah dan penanaman padi. Waspadai hujan singkat di sore hari.",
            hourlyForecast = listOf(
                HourlyForecast("14:00", "☀️", 30),
                HourlyForecast("15:00", "🌦️", 29),
                HourlyForecast("16:00", "🌦️", 28),
                HourlyForecast("17:00", "☁️", 28),
                HourlyForecast("18:00", "🌙", 27)
            ),
            dailyForecast = listOf(
                DailyForecast("Besok", "🌦️", 30, 24),
                DailyForecast("Selasa", "☀️", 31, 25),
                DailyForecast("Rabu", "⛈️", 29, 24),
                DailyForecast("Kamis", "☀️", 32, 25),
                DailyForecast("Jumat", "🌦️", 30, 24)
            ),
            wind = "10 km/j",
            humidity = "75%",
            uvIndex = "Tinggi",
            pressure = "1012 hPa"
        )
    }
}

