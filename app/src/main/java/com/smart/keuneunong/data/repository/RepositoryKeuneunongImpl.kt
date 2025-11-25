package com.smart.keuneunong.data.repository

import com.smart.keuneunong.data.model.KeuneunongPhase
import com.smart.keuneunong.domain.repository.RepositoryKeuneunong
import javax.inject.Inject

class RepositoryKeuneunongImpl @Inject constructor(): RepositoryKeuneunong {
    override fun getPhases(): List<KeuneunongPhase> {
        // Example data, replace with actual logic
        return listOf(
            KeuneunongPhase(
                id = 1,
                name = "Musim Tanam",
                icon = "\uD83C\uDF31",
                description = "Waktu penanaman padi dan palawija",
                startDate = 1636329600000, // 8 Nov 2021
                endDate = 1636934400000,   // 15 Nov 2021
                activities = listOf("Menanam padi", "Menanam palawija")
            ),
            KeuneunongPhase(
                id = 2,
                name = "Musim Hujan",
                icon = "\uD83C\uDF27\uFE0F",
                description = "Curah hujan meningkat",
                startDate = 1636934400000, // 15 Nov 2021
                endDate = 1637539200000,   // 22 Nov 2021
                activities = listOf("Persiapan lahan", "Pengairan")
            ),
            KeuneunongPhase(
                id = 3,
                name = "Waktu Melaut",
                icon = "\uD83C\uDF0A",
                description = "Kondisi laut aman untuk nelayan",
                startDate = 1637539200000, // 22 Nov 2021
                endDate = 1638144000000,   // 29 Nov 2021
                activities = listOf("Melaut", "Menangkap ikan")
            )
        )
    }

    override fun getPhaseById(id: Int): KeuneunongPhase? {
        return getPhases().find { it.id == id }
    }
}
