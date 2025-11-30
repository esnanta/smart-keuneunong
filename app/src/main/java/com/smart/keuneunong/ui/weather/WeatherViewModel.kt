package com.smart.keuneunong.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.domain.repository.WeatherRepository
import com.smart.keuneunong.ui.location.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        loadWeather()
    }

    fun loadWeather(locationState: LocationState? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                // Extract latitude and longitude from locationState
                val (latitude, longitude) = when (locationState) {
                    is LocationState.Success -> {
                        Pair(locationState.latitude, locationState.longitude)
                    }
                    else -> {
                        // Default location (e.g., Lhokseumawe, Aceh)
                        Pair(5.1797, 97.1406)
                    }
                }

                // Fetch weather data from repository
                val weatherData = weatherRepository.getWeather(latitude, longitude)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    weatherData = weatherData,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Gagal memuat data cuaca"
                )
            }
        }
    }
}
