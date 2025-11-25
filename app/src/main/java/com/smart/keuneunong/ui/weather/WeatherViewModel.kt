package com.smart.keuneunong.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.data.model.WeatherData
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
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Simulate network delay
                kotlinx.coroutines.delay(1500)

                val sampleWeatherData = WeatherData(
                    temperature = 30,
                    condition = "Cerah Berawan",
                    humidity = 75,
                    windSpeed = 10,
                    weatherIcon = "☀️",
                    feelsLike = 32,
                    location = "Lhokseumawe",
                    date = System.currentTimeMillis()
                )
                _uiState.value = _uiState.value.copy(isLoading = false, weatherData = sampleWeatherData, error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
