package com.smart.keuneunong.data.repository

import com.smart.keuneunong.data.model.WeatherData
import com.smart.keuneunong.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherData {
        // Dummy data matching the new UI
        return WeatherData(
            temperature = 29,
            condition = "Cerah Berawan",
            humidity = 72,
            windSpeed = 14,
            weatherIcon = "☀️", // Placeholder
            feelsLike = 32,
            location = "Lhokseumawe",
            date = System.currentTimeMillis(),
            sunrise = "06:18",
            sunset = "18:45",
            waveHeightMin = 0.5,
            waveHeightMax = 1.2,
            seaWindSpeed = 18,
            seaWindDirection = "Barat",
            keuneunongContext = "Saat ini: Keuneunong Ròt (Musim Tanam)\nCuaca cerah berawan sangat baik untuk memulai pengolahan sawah dan penanaman padi. Waspadai hujan singkat di sore hari.",
            seaConditionSummary = "Kondisi laut cukup tenang, aman untuk aktivitas melaut. Tetap waspada terhadap perubahan cuaca tiba-tiba."
        )
    }
}

