package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.model.CalendarDayData

interface CalendarRepository {
    fun getCalendarDays(month: Int, year: Int): List<CalendarDayData>
    fun getMonthName(month: Int): String
}