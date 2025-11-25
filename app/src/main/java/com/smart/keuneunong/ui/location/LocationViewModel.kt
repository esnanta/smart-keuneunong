package com.smart.keuneunong.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.data.preferences.LocationPreferencesManager
import com.smart.keuneunong.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationPreferences: LocationPreferencesManager,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _selectedLocation = MutableStateFlow<LocationState>(LocationState.Initial)
    val selectedLocation: StateFlow<LocationState> = _selectedLocation.asStateFlow()

    init {
        loadSavedLocation()
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _selectedLocation.value = LocationState.Loading

            try {
                locationPreferences.saveLocation(latitude, longitude)

                _selectedLocation.value = LocationState.Success(
                    latitude = latitude,
                    longitude = longitude
                )
            } catch (e: Exception) {
                _selectedLocation.value = LocationState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadSavedLocation() {
        viewModelScope.launch {
            try {
                val location = locationPreferences.getLocation()

                if (location != null) {
                    _selectedLocation.value = LocationState.Success(
                        latitude = location.first,
                        longitude = location.second
                    )
                } else {
                    _selectedLocation.value = LocationState.Initial
                }
            } catch (e: Exception) {
                _selectedLocation.value = LocationState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun clearLocation() {
        viewModelScope.launch {
            try {
                locationPreferences.clearLocation()
                _selectedLocation.value = LocationState.Initial
            } catch (e: Exception) {
                _selectedLocation.value = LocationState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getCityName(latitude: Double, longitude: Double): String {
        return locationRepository.getCityName(latitude, longitude)
    }
}

sealed class LocationState {
    object Initial : LocationState()
    object Loading : LocationState()
    data class Success(val latitude: Double, val longitude: Double) : LocationState()
    data class Error(val message: String) : LocationState()
}
