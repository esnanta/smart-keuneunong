package com.smart.keuneunong.ui.weather

import androidx.compose.runtime.Immutable
import com.smart.keuneunong.data.model.WeatherData

 @Immutable
 data class WeatherUiState(
     val weatherData: WeatherData? = null,
     val isLoading: Boolean = false,
     val error: String? = null
)

