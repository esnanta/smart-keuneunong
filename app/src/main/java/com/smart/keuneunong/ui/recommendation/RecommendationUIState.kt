package com.smart.keuneunong.ui.recommendation

data class SectorRecommendation(
    val title: String,
    val icon: String,
    val recommendations: List<String>,
    val notes: String?
)

sealed class RecommendationUiState {
    object Loading : RecommendationUiState()
    data class Success(
        val periodName: String,
        val periodDescription: String,
        val sectorRecommendations: List<SectorRecommendation>
    ) : RecommendationUiState()
    data class Error(val message: String) : RecommendationUiState()
}