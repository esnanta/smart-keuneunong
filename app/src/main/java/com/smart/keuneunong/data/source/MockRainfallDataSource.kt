package com.smart.keuneunong.data.source

import com.smart.keuneunong.domain.model.RainfallCategory
import com.smart.keuneunong.domain.model.RainfallHistory
import javax.inject.Inject
import kotlin.random.Random

/**
 * Mock Data Source untuk data historis curah hujan
 * Bagian dari Data Layer - Clean Architecture
 *
 * Menyediakan data mockup dengan distribusi kategori yang bervariasi
 */
class MockRainfallDataSource @Inject constructor() {

    /**
     * Generate mock rainfall data untuk bulan tertentu
     * Distribusi kategori dibuat bervariasi untuk menunjukkan semua warna
     */
    fun generateMockRainfallData(month: Int, year: Int): List<RainfallHistory> {
        val daysInMonth = getDaysInMonth(month, year)
        val rainfallData = mutableListOf<RainfallHistory>()

        // Pattern untuk distribusi kategori yang bervariasi
        val categoryPattern = listOf(
            RainfallCategory.TINGGI,
            RainfallCategory.SEDANG,
            RainfallCategory.SEDANG,
            RainfallCategory.RENDAH,
            RainfallCategory.RENDAH,
            RainfallCategory.RENDAH,
            RainfallCategory.SANGAT_RENDAH,
            RainfallCategory.TINGGI,
            RainfallCategory.SEDANG,
            RainfallCategory.RENDAH
        )

        for (day in 1..daysInMonth) {
            val category = categoryPattern[day % categoryPattern.size]
            val amount = when (category) {
                RainfallCategory.TINGGI -> Random.nextDouble(100.0, 200.0)
                RainfallCategory.SEDANG -> Random.nextDouble(50.0, 99.9)
                RainfallCategory.RENDAH -> Random.nextDouble(10.0, 49.9)
                RainfallCategory.SANGAT_RENDAH -> Random.nextDouble(0.0, 9.9)
            }

            rainfallData.add(
                RainfallHistory(
                    day = day,
                    month = month,
                    year = year,
                    category = category,
                    amount = amount
                )
            )
        }

        return rainfallData
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
}

