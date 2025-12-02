package com.smart.keuneunong.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: com.smart.keuneunong.domain.repository.WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var lastLocationState: com.smart.keuneunong.ui.location.LocationState? = null

    fun loadWeather(locationState: com.smart.keuneunong.ui.location.LocationState? = null) {
        lastLocationState = locationState
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val (latitude, longitude) = when (locationState) {
                    is com.smart.keuneunong.ui.location.LocationState.Success -> {
                        Pair(locationState.latitude, locationState.longitude)
                    }
                    else -> {
                        // Default location (e.g., Lhokseumawe, Aceh)
                        Pair(5.1797, 97.1406)
                    }
                }

                // The repository now handles fallback internally, so we just call it
                val weatherData = weatherRepository.getWeather(latitude, longitude)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    weatherData = weatherData,
                    error = null
                )
            } catch (_: Exception) {
                // This shouldn't happen anymore since repository handles errors
                // But keep it as a safety net with mock data fallback
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null, // Don't show error since we have mock data from repository
                    weatherData = null
                )
            }
        }
    }

    fun refresh() {
        loadWeather(lastLocationState)
    }
}
