package com.smart.keuneunong.data.model

data class WeatherData(
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Int,
    val weatherIcon: String,
    val feelsLike: Int,
    val location: String,
    val date: Long,
    val sunrise: String,
    val sunset: String,
    val waveHeightMin: Double,
    val waveHeightMax: Double,
    val seaWindSpeed: Int,
    val seaWindDirection: String,
    val keuneunongContext: String,
    val seaConditionSummary: String
)
