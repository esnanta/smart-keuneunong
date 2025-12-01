package com.smart.keuneunong.ui.home

import com.smart.keuneunong.data.model.CalendarDayData
import java.util.Calendar

data class HomeUiState(
    val currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1,
    val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val calendarDays: List<CalendarDayData> = emptyList(),
    val today: Triple<Int, Int, Int> = with(Calendar.getInstance()) {
        Triple(get(Calendar.DAY_OF_MONTH), get(Calendar.MONTH) + 1, get(Calendar.YEAR))
    },
    val isLoading: Boolean = false,
    val error: String? = null
)
