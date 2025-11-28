package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.domain.model.RainfallHistory

/**
 * Repository interface untuk data historis curah hujan
 * Bagian dari Domain Layer - Clean Architecture
 */
interface RainfallRepository {
    suspend fun getRainfallHistory(month: Int, year: Int): List<RainfallHistory>
    suspend fun getRainfallForDay(day: Int, month: Int, year: Int): RainfallHistory?
}

