package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.domain.model.RainfallHistory

interface CalendarRepository {
    fun getCalendarDays(month: Int, year: Int, rainfallData: List<RainfallHistory> = emptyList()): List<CalendarDayData>
    fun getMonthName(month: Int): String
}