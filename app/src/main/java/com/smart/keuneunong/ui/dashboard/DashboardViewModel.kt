package com.smart.keuneunong.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadCalendar(_uiState.value.currentMonth, _uiState.value.currentYear)
    }

    fun onPreviousMonth() {
        val (month, year) = with(_uiState.value) {
            if (currentMonth == 1) 12 to currentYear - 1 else currentMonth - 1 to currentYear
        }
        loadCalendar(month, year)
    }

    fun onNextMonth() {
        val (month, year) = with(_uiState.value) {
            if (currentMonth == 12) 1 to currentYear + 1 else currentMonth + 1 to currentYear
        }
        loadCalendar(month, year)
    }

    private fun loadCalendar(month: Int, year: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                currentMonth = month,
                currentYear = year,
                calendarDays = getCalendarDays(month, year)
            )
        }
    }

    // Move calendar logic here (from composable)
    private fun getCalendarDays(month: Int, year: Int): List<CalendarDayData> {
        val days = mutableListOf<CalendarDayData>()
        val firstDayOfWeek = getFirstDayOfMonth(month, year)
        val daysInMonth = getDaysInMonth(month, year)
        repeat(firstDayOfWeek) { days.add(CalendarDayData(day = 0)) }
        for (day in 1..daysInMonth) {
            val today = DateUtils.getCurrentDay() == day && DateUtils.getCurrentMonth() == month && DateUtils.getCurrentYear() == year
            val weatherEmoji = when {
                day in listOf(3, 10, 17, 24) -> "🌧️"
                day in listOf(2, 7, 9, 11, 14, 18, 21, 25, 27) -> "⛅"
                day in listOf(16, 23, 29) -> "☁️"
                else -> "☀️"
            }
            val hasSpecialEvent = day in listOf(15, 22)
            days.add(CalendarDayData(day, today, weatherEmoji, hasSpecialEvent))
        }
        val totalCells = days.size
        val remainingCells = if (totalCells % 7 != 0) 7 - (totalCells % 7) else 0
        repeat(remainingCells) { days.add(CalendarDayData(day = 0)) }
        return days
    }

    private fun getFirstDayOfMonth(month: Int, year: Int): Int {
        if (month == 11 && year == 2025) return 6
        val m = if (month < 3) month + 12 else month
        val y = if (month < 3) year - 1 else year
        val k = y % 100
        val j = y / 100
        val h = (1 + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7
        return (h + 6) % 7
    }

    private fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> 30
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "Januari"
            2 -> "Februari"
            3 -> "Maret"
            4 -> "April"
            5 -> "Mei"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "Agustus"
            9 -> "September"
            10 -> "Oktober"
            11 -> "November"
            12 -> "Desember"
            else -> "Unknown"
        }
    }
}
