package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.ui.recommendation.RecommendationUiState
import com.smart.keuneunong.ui.recommendation.SectorRecommendation
import kotlinx.coroutines.delay
import javax.inject.Inject

class RecommendationRepository @Inject constructor() {
    suspend fun getRecommendationData(): RecommendationUiState {
        delay(500) // Simulasi loading
        return RecommendationUiState.Success(
            periodName = "Keuneunong Ke-7 (Tujuh)",
            periodDescription = "Masa 'Angen Barat' (Angin Barat) dimulai. Curah hujan sedang hingga tinggi dan angin cenderung kencang.",
            sectorRecommendations = listOf(
                SectorRecommendation(
                    title = "Pertanian",
                    icon = "🌾",
                    recommendations = listOf(
                        "Waktu ideal untuk memulai penanaman padi sawah.",
                        "Periksa saluran irigasi untuk memastikan ketersediaan air.",
                        "Perbanyak pemupukan karena nutrisi mungkin terbawa air hujan."
                    ),
                    notes = "Waspada hama wereng dan busuk akar akibat kelembaban tinggi."
                ),
                SectorRecommendation(
                    title = "Melaut & Perikanan",
                    icon = "🌊",
                    recommendations = listOf(
                        "Musim ikan tongkol dan cumi-cumi di perairan dangkal.",
                        "Waktu yang baik untuk memperbaiki jaring dan alat tangkap."
                    ),
                    notes = "Angin Barat sedang kencang. Ombak bisa mencapai 2-3 meter. Nelayan kecil disarankan tidak melaut jauh."
                ),
                SectorRecommendation(
                    title = "Aktivitas Lain",
                    icon = "🏠",
                    recommendations = listOf(
                        "Waktu yang baik untuk memperbaiki atap rumah sebelum puncak musim hujan.",
                        "Jaga kesehatan karena masuk musim pancaroba."
                    ),
                    notes = null
                )
            )
        )
    }
}