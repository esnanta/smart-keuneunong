package com.smart.keuneunong.data.model

data class CalendarDayData(
    val day: Int,
    val isToday: Boolean = false,
    val weatherEmoji: String = "☀️",
    val hasSpecialEvent: Boolean = false
)