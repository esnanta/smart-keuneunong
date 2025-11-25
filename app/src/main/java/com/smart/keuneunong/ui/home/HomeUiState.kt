package com.smart.keuneunong.ui.home

import com.smart.keuneunong.data.model.CalendarDayData

data class HomeUiState(
    val currentMonth: Int = 11,
    val currentYear: Int = 2025,
    val calendarDays: List<CalendarDayData> = emptyList(),
    val today: Triple<Int, Int, Int> = Triple(13, 11, 2025), // day, month, year
    val isLoading: Boolean = false,
    val error: String? = null
)
