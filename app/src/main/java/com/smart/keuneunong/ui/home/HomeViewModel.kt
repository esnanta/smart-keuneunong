package com.smart.keuneunong.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.smart.keuneunong.domain.repository.CalendarRepository
import com.smart.keuneunong.domain.usecase.GetRainfallHistoryUseCase
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.smart.keuneunong.domain.repository.RepositoryKeuneunong

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val getRainfallHistoryUseCase: GetRainfallHistoryUseCase,
    val repositoryKeuneunong: RepositoryKeuneunong
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCalendar(_uiState.value.currentMonth, _uiState.value.currentYear)
    }

    fun onPreviousMonth() {
        val (month, year) = with(_uiState.value) {
            if (currentMonth == 1) 12 to currentYear - 1 else currentMonth - 1 to currentYear
        }
        loadCalendar(month, year)
    }

    fun onNextMonth() {
        val (month, year) = with(_uiState.value) {
            if (currentMonth == 12) 1 to currentYear + 1 else currentMonth + 1 to currentYear
        }
        loadCalendar(month, year)
    }

    private fun loadCalendar(month: Int, year: Int, latitude: Double = 5.55, longitude: Double = 95.32) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Fetch rainfall data from use case
                val rainfallData = getRainfallHistoryUseCase(month, year)

                // Get calendar days with rainfall data
                val calendarDays = calendarRepository.getCalendarDays(month, year, rainfallData, latitude, longitude)

                _uiState.value = _uiState.value.copy(
                    currentMonth = month,
                    currentYear = year,
                    calendarDays = calendarDays,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun getMonthName(month: Int): String {
        return calendarRepository.getMonthName(month)
    }
}
