package com.smart.keuneunong.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.keuneunong.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {
    val selectedLocation: StateFlow<LocationState> = repository.locationState

    init {
        viewModelScope.launch {
            repository.loadSavedLocation()
        }
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.saveLocation(latitude, longitude)
        }
    }

    fun clearLocation() {
        viewModelScope.launch {
            repository.clearLocation()
        }
    }

    fun getLocationName(latitude: Double, longitude: Double): String {
        return repository.getLocationName(latitude, longitude)
    }
}

sealed class LocationState {
    object Initial : LocationState()
    object Loading : LocationState()
    data class Success(val latitude: Double, val longitude: Double) : LocationState()
    data class Error(val message: String) : LocationState()
}
