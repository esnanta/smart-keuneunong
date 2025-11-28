package com.smart.keuneunong.domain.usecase

import com.smart.keuneunong.domain.model.RainfallHistory
import com.smart.keuneunong.domain.repository.RainfallRepository
import javax.inject.Inject

/**
 * Use Case untuk mendapatkan data historis curah hujan
 * Bagian dari Domain Layer - Clean Architecture
 *
 * Mengandung logika bisnis untuk mengambil dan memproses data curah hujan
 */
class GetRainfallHistoryUseCase @Inject constructor(
    private val rainfallRepository: RainfallRepository
) {
    /**
     * Mendapatkan data historis curah hujan untuk bulan tertentu
     */
    suspend operator fun invoke(month: Int, year: Int): List<RainfallHistory> {
        return rainfallRepository.getRainfallHistory(month, year)
    }

    /**
     * Mendapatkan data curah hujan untuk hari tertentu
     */
    suspend fun getForDay(day: Int, month: Int, year: Int): RainfallHistory? {
        return rainfallRepository.getRainfallForDay(day, month, year)
    }
}

