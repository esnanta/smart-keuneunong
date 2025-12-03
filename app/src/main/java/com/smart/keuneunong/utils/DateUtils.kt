package com.smart.keuneunong.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

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

    fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }

    fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun getCurrentDay(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    fun getDaysInMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getStartDayOfWeek(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return sdf.format(Date(timestamp))
    }

    fun getWeatherIconForDay(day: Int): String {
        // Simple weather icon rotation for demo
        return when (day % 4) {
            0 -> "☀️"
            1 -> "⛅"
            2 -> "🌤️"
            3 -> "🌦️"
            else -> "☀️"
        }
    }

    /**
     * Get Hijri (Islamic) date for current date
     * Using Umm al-Qura calendar calculation
     */
    fun getCurrentHijriDate(): HijriDate {
        val calendar = Calendar.getInstance()
        return gregorianToHijri(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    /**
     * Convert Gregorian date to Hijri date
     * Simplified Umm al-Qura algorithm
     */
    fun gregorianToHijri(year: Int, month: Int, day: Int): HijriDate {
        // Julian day calculation
        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3
        val jd = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045

        // Convert Julian day to Hijri
        val l = jd - 1948440 + 10632
        val n = (l - 1) / 10631
        val l2 = l - 10631 * n + 354
        val j = ((10985 - l2) / 5316) * ((50 * l2) / 17719) + (l2 / 5670) * ((43 * l2) / 15238)
        val l3 = l2 - ((30 - j) / 15) * ((17719 * j) / 50) - (j / 16) * ((15238 * j) / 43) + 29

        val hijriMonth = (24 * l3) / 709
        val hijriDay = l3 - (709 * hijriMonth) / 24
        val hijriYear = 30 * n + j - 30

        return HijriDate(hijriYear, hijriMonth, hijriDay)
    }

    fun getUroeBuleunMonthName(month: Int): String {
        return when (month) {
            1 -> "Asan Usen"
            2 -> "Sapha"
            3 -> "Molot Phon"
            4 -> "Adoe Molot"
            5 -> "Molot Seuneulheuh"
            6 -> "Khanduri Boh Kayee"
            7 -> "Khanduri Apam"
            8 -> "Khanduri Bu"
            9 -> "Puasa"
            10 -> "Uroe Raya"
            11 -> "Meuapet"
            12 -> "Haji"
            else -> "Unknown"
        }
    }

    fun getHijriMonthName(month: Int): String {
        return when (month) {
            1 -> "Muharram"
            2 -> "Safar"
            3 -> "Rabiul Awal"
            4 -> "Rabiul Akhir"
            5 -> "Jumadil Awal"
            6 -> "Jumadil Akhir"
            7 -> "Rajab"
            8 -> "Syaban"
            9 -> "Ramadan"
            10 -> "Syawal"
            11 -> "Zulkaidah"
            12 -> "Zulhijjah"
            else -> "Unknown"
        }
    }

    data class HijriDate(
        val year: Int,
        val month: Int,
        val day: Int
    ) {
        fun format(): String = "$day ${getUroeBuleunMonthName(month)}"
        fun formatWithYear(): String = "$day ${getUroeBuleunMonthName(month)} $year H"
        fun formatHijriWithYear(): String = "$day ${getHijriMonthName(month)} $year H"
    }
}
