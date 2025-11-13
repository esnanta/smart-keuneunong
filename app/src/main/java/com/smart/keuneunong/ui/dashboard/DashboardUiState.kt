package com.smart.keuneunong.ui.dashboard

import com.smart.keuneunong.data.model.CalendarDayData

// Represents the UI state for the Dashboard screen
// Add more fields as needed for features, loading, error, etc.
data class DashboardUiState(
    val currentMonth: Int = 11,
    val currentYear: Int = 2025,
    val calendarDays: List<CalendarDayData> = emptyList(),
    val today: Triple<Int, Int, Int> = Triple(13, 11, 2025), // day, month, year
    val isLoading: Boolean = false,
    val error: String? = null
)
