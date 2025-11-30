package com.smart.keuneunong.data.repository

import com.smart.keuneunong.data.source.MockRainfallDataSource
import com.smart.keuneunong.domain.model.RainfallHistory
import com.smart.keuneunong.domain.repository.RainfallRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Implementasi Repository untuk data historis curah hujan
 * Bagian dari Data Layer - Clean Architecture
 */
class RainfallRepositoryImpl @Inject constructor(
    private val mockDataSource: MockRainfallDataSource
) : RainfallRepository {

    // Cache untuk menyimpan data yang sudah di-generate
    private val cache = mutableMapOf<String, List<RainfallHistory>>()

    override suspend fun getRainfallHistory(month: Int, year: Int): List<RainfallHistory> {
        // Simulasi network delay
        delay(300)

        val key = "$month-$year"
        return cache.getOrPut(key) {
            mockDataSource.generateMockRainfallData(month, year)
        }
    }

    override suspend fun getRainfallForDay(day: Int, month: Int, year: Int): RainfallHistory? {
        val history = getRainfallHistory(month, year)
        return history.find { it.day == day }
    }
}

