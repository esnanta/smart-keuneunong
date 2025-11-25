package com.smart.keuneunong.ui.weather

import androidx.compose.ui.graphics.vector.ImageVector

data class WeatherData(
    val location: String,
    val temperature: Int,
    val condition: String,
    val weatherIcon: String,
    val keuneunongContext: String,
    val keuneunongDescription: String,
    val hourlyForecast: List<HourlyForecast>,
    val dailyForecast: List<DailyForecast>,
    val wind: String,
    val humidity: String,
    val uvIndex: String,
    val pressure: String
)

data class HourlyForecast(
    val time: String,
    val icon: String,
    val temperature: Int
)

data class DailyForecast(
    val day: String,
    val icon: String,
    val tempMax: Int,
    val tempMin: Int
)

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weatherData: WeatherData) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

