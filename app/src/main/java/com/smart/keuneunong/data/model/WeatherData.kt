package com.smart.keuneunong.data.model

data class WeatherData(
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Int,
    val weatherIcon: String,
    val feelsLike: Int,
    val location: String,
    val date: Long
)
