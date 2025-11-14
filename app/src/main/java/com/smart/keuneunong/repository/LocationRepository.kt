package com.smart.keuneunong.repository

import com.smart.keuneunong.data.preferences.LocationPreferencesManager
import com.smart.keuneunong.ui.location.LocationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

@Singleton
class LocationRepository @Inject constructor(
    private val locationPreferences: LocationPreferencesManager
) {
    private val _locationState = MutableStateFlow<LocationState>(LocationState.Initial)
    val locationState: StateFlow<LocationState> = _locationState.asStateFlow()

    fun saveLocation(latitude: Double, longitude: Double) {
        _locationState.value = LocationState.Loading
        try {
            locationPreferences.saveLocation(latitude, longitude)
            _locationState.value = LocationState.Success(latitude, longitude)
        } catch (e: Exception) {
            _locationState.value = LocationState.Error(e.message ?: "Unknown error")
        }
    }

    fun loadSavedLocation() {
        try {
            val location = locationPreferences.getLocation()
            if (location != null) {
                _locationState.value = LocationState.Success(location.first, location.second)
            } else {
                _locationState.value = LocationState.Initial
            }
        } catch (e: Exception) {
            _locationState.value = LocationState.Error(e.message ?: "Unknown error")
        }
    }

    fun clearLocation() {
        try {
            locationPreferences.clearLocation()
            _locationState.value = LocationState.Initial
        } catch (e: Exception) {
            _locationState.value = LocationState.Error(e.message ?: "Unknown error")
        }
    }

    fun getLocationName(latitude: Double, longitude: Double): String {
        val acehnesseCities = listOf(
            Triple(5.5577, 95.3222, "Banda Aceh"),
            Triple(5.1801, 97.1507, "Lhokseumawe"),
            Triple(5.0915, 97.3174, "Langsa"),
            Triple(4.1721, 96.2524, "Meulaboh"),
            Triple(3.9670, 97.0253, "Tapaktuan"),
            Triple(4.3726, 97.7925, "Singkil"),
            Triple(5.5291, 96.9490, "Bireuen"),
            Triple(4.5159, 96.4167, "Calang"),
            Triple(5.1895, 96.7446, "Sigli"),
            Triple(4.9637, 97.6274, "Idi"),
            Triple(5.3090, 96.8097, "Lhoksukon"),
            Triple(4.6951, 96.2493, "Jantho"),
            Triple(4.3588, 97.1953, "Blangkejeren"),
            Triple(4.0329, 96.8157, "Kutacane"),
            Triple(5.0260, 97.4012, "Kuala Simpang"),
            Triple(4.8401, 97.1534, "Takengon"),
            Triple(5.2491, 96.5039, "Lhoknga"),
            Triple(5.4560, 95.6203, "Sabang")
        )
        val tolerance = 0.1
        for ((cityLat, cityLng, cityName) in acehnesseCities) {
            val latDiff = abs(latitude - cityLat)
            val lngDiff = abs(longitude - cityLng)
            if (latDiff <= tolerance && lngDiff <= tolerance) {
                return cityName
            }
        }
        return "Aceh"
    }
}