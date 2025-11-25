package com.smart.keuneunong.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.smart.keuneunong.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
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

    private fun loadCalendar(month: Int, year: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                currentMonth = month,
                currentYear = year,
                calendarDays = calendarRepository.getCalendarDays(month, year)
            )
        }
    }

    fun getMonthName(month: Int): String {
        return calendarRepository.getMonthName(month)
    }
}
