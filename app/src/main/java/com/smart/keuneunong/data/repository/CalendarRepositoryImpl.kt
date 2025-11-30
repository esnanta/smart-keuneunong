package com.smart.keuneunong.data.repository

import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.data.network.WeatherApi
import com.smart.keuneunong.data.preferences.LocationPreferencesManager
import com.smart.keuneunong.domain.model.RainfallHistory
import com.smart.keuneunong.domain.repository.CalendarRepository
import com.smart.keuneunong.utils.DateUtils

class CalendarRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val locationPreferencesManager: LocationPreferencesManager
) : CalendarRepository {
    override fun getCalendarDays(month: Int, year: Int, rainfallData: List<RainfallHistory>): List<CalendarDayData> {
        val days = mutableListOf<CalendarDayData>()
        val firstDayOfWeek = getFirstDayOfMonth(month, year)
        val daysInMonth = getDaysInMonth(month, year)

        // Create rainfall map for quick lookup
        val rainfallMap = rainfallData.associateBy { it.day }

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
            val rainfallCategory = rainfallMap[day]?.category

            days.add(CalendarDayData(day, today, weatherEmoji, hasSpecialEvent, rainfallCategory))
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

    override fun getMonthName(month: Int): String {
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

