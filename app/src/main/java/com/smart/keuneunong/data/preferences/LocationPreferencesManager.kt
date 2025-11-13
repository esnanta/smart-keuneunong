package com.smart.keuneunong.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "location_prefs"
        private const val KEY_LATITUDE = "user_latitude"
        private const val KEY_LONGITUDE = "user_longitude"
        private const val KEY_HAS_LOCATION = "has_location"
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        preferences.edit().apply {
            putString(KEY_LATITUDE, latitude.toString())
            putString(KEY_LONGITUDE, longitude.toString())
            putBoolean(KEY_HAS_LOCATION, true)
            apply()
        }
    }

    fun getLocation(): Pair<Double, Double>? {
        if (!hasLocation()) return null

        val latitude = preferences.getString(KEY_LATITUDE, null)?.toDoubleOrNull() ?: return null
        val longitude = preferences.getString(KEY_LONGITUDE, null)?.toDoubleOrNull() ?: return null

        return Pair(latitude, longitude)
    }

    fun hasLocation(): Boolean {
        return preferences.getBoolean(KEY_HAS_LOCATION, false)
    }

    fun clearLocation() {
        preferences.edit().apply {
            remove(KEY_LATITUDE)
            remove(KEY_LONGITUDE)
            putBoolean(KEY_HAS_LOCATION, false)
            apply()
        }
    }

    fun getLatitude(): Double? {
        return preferences.getString(KEY_LATITUDE, null)?.toDoubleOrNull()
    }

    fun getLongitude(): Double? {
        return preferences.getString(KEY_LONGITUDE, null)?.toDoubleOrNull()
    }
}

