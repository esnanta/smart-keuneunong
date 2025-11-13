package com.smart.keuneunong.ui.location

import com.google.android.gms.maps.model.LatLng

/**
 * UI State for LocationPickerScreen
 * Manages all UI-related state in a single, cohesive data class
 */
data class LocationUIState(
    val selectedLocation: LatLng? = null,
    val hasLocationPermission: Boolean = false,
    val isLoadingLocation: Boolean = false,
    val errorMessage: String? = null,
    val showPermissionRationale: Boolean = false
) {
    val canConfirmLocation: Boolean
        get() = selectedLocation != null && !isLoadingLocation

    val shouldShowMap: Boolean
        get() = hasLocationPermission

    val hasError: Boolean
        get() = errorMessage != null
}

