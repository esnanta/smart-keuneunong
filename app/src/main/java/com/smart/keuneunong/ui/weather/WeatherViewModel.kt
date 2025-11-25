package com.smart.keuneunong.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        loadWeatherData()
    }

    private fun loadWeatherData() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {
                val weatherData = repository.getWeatherData()
                _uiState.value = WeatherUiState.Success(weatherData)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Failed to load weather data.")
            }
        }
    }
}

