package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.domain.model.RainfallHistory

interface CalendarRepository {
    suspend fun getCalendarDays(
        month: Int,
        year: Int,
        rainfallData: List<RainfallHistory> = emptyList(),
        latitude: Double,
        longitude: Double
    ): List<CalendarDayData>
    fun getMonthName(month: Int): String
}