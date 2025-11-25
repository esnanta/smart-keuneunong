package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.ui.recommendation.RecommendationUiState

interface RecommendationRepository {
    suspend fun getRecommendationData(): RecommendationUiState
}

