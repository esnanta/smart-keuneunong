package com.smart.keuneunong.data.model

import com.smart.keuneunong.domain.model.RainfallCategory

data class CalendarDayData(
    val day: Int,
    val isToday: Boolean = false,
    val weatherEmoji: String = "☀️",
    val hasSpecialEvent: Boolean = false,
    val rainfallCategory: RainfallCategory? = null
)