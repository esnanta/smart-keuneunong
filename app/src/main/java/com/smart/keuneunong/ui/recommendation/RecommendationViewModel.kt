package com.smart.keuneunong.ui.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.repository.RecommendationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val repository: RecommendationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecommendationUiState>(RecommendationUiState.Loading)
    val uiState: StateFlow<RecommendationUiState> = _uiState.asStateFlow()

    init {
        loadRecommendation()
    }

    private fun loadRecommendation() {
        viewModelScope.launch {
            try {
                _uiState.value = RecommendationUiState.Loading
                val result = repository.getRecommendationData()
                _uiState.value = result
            } catch (e: Exception) {
                _uiState.value = RecommendationUiState.Error("Gagal memuat rekomendasi.")
            }
        }
    }
}
