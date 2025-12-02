package com.smart.keuneunong.data.repository

import com.smart.keuneunong.data.model.KeuneunongPhase
import com.smart.keuneunong.domain.repository.RepositoryKeuneunong
import java.util.Calendar
import javax.inject.Inject

class KeuneunongRepositoryImpl @Inject constructor() : RepositoryKeuneunong {
    override fun getPhases(): List<KeuneunongPhase> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        // Example data, dynamically generated for the current month
        return listOf(
            KeuneunongPhase(
                id = 1,
                name = "Musim Tanam",
                icon = "🌱",
                description = "Waktu penanaman padi dan palawija",
                startDate = getStartDate(year, month, 1).timeInMillis,
                endDate = getEndDate(year, month, 7).timeInMillis,
                activities = listOf("Menanam padi", "Menanam palawija")
            ),
            KeuneunongPhase(
                id = 2,
                name = "Musim Hujan",
                icon = "🌧️",
                description = "Curah hujan meningkat",
                startDate = getStartDate(year, month, 8).timeInMillis,
                endDate = getEndDate(year, month, 14).timeInMillis,
                activities = listOf("Persiapan lahan", "Pengairan")
            ),
            KeuneunongPhase(
                id = 3,
                name = "Waktu Melaut",
                icon = "🌊",
                description = "Kondisi laut aman untuk nelayan",
                startDate = getStartDate(year, month, 15).timeInMillis,
                endDate = getEndDate(year, month, 21).timeInMillis,
                activities = listOf("Melaut", "Menangkap ikan")
            )
        )
    }

    private fun getStartDate(year: Int, month: Int, day: Int): Calendar {
        return Calendar.getInstance().apply {
            set(year, month, day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    private fun getEndDate(year: Int, month: Int, day: Int): Calendar {
        return Calendar.getInstance().apply {
            set(year, month, day, 23, 59, 59)
            set(Calendar.MILLISECOND, 999)
        }
    }

    override fun getPhaseById(id: Int): KeuneunongPhase? {
        return getPhases().find { it.id == id }
    }
}
