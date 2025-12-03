package com.smart.keuneunong.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.smart.keuneunong.ui.home.AboutDialog
import com.smart.keuneunong.ui.location.LocationPickerScreen
import com.smart.keuneunong.ui.location.LocationViewModel
import com.smart.keuneunong.ui.weather.WeatherViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun ScreenWithHeaderAndDrawer(
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    content: @Composable (PaddingValues, (Triple<Int, Int, Int>) -> String, WeatherViewModel) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showAboutDialog by remember { mutableStateOf(false) }
    var showLocationPicker by remember { mutableStateOf(false) }
    var showCityPicker by remember { mutableStateOf(false) }

    // Collect weather data from ViewModel
    val weatherUiState by weatherViewModel.uiState.collectAsState()
    val locationState by locationViewModel.selectedLocation.collectAsState()

    // Defer weather loading until after initial composition
    // This prevents blocking the main thread during UI rendering
    LaunchedEffect(Unit) {
        // Initial load with default or saved location
        weatherViewModel.loadWeather(
            if (locationState is com.smart.keuneunong.ui.location.LocationState.Success) {
                locationState as com.smart.keuneunong.ui.location.LocationState.Success
            } else null
        )
    }

    // Reload weather when location changes
    LaunchedEffect(locationState) {
        if (locationState is com.smart.keuneunong.ui.location.LocationState.Success) {
            val location = locationState as com.smart.keuneunong.ui.location.LocationState.Success
            weatherViewModel.loadWeather(location)
        }
    }

    val today = Calendar.getInstance()
    val getMonthName = { date: Triple<Int, Int, Int> ->
        val calendar = Calendar.getInstance().apply {
            set(date.first, date.second - 1, date.third)
        }
        String.format("%tB", calendar)
    }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }

    if (showLocationPicker) {
        LocationPickerScreen(
            locationViewModel = locationViewModel,
            onDismiss = { showLocationPicker = false }
        )
    }

    if (showCityPicker) {
        CityPickerDialog(
            onDismiss = { showCityPicker = false },
            onCitySelected = { city ->
                // Save the selected city location
                locationViewModel.saveLocation(city.latitude, city.longitude)
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onShowAboutDialog = {
                    showAboutDialog = true
                    scope.launch { drawerState.close() }
                },
                onShowLocationPicker = {
                    showLocationPicker = true
                    scope.launch { drawerState.close() }
                },
                onShowCityPicker = {
                    showCityPicker = true
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                Column {
                    ScreenHeader(
                        currentDate = Triple(
                            today.get(Calendar.YEAR),
                            today.get(Calendar.MONTH) + 1,
                            today.get(Calendar.DAY_OF_MONTH)
                        ),
                        getMonthName = { month ->
                            val calendar = Calendar.getInstance()
                            calendar.set(Calendar.MONTH, month - 1)
                            String.format("%tB", calendar)
                        },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        weatherData = weatherUiState.weatherData,
                        isLoadingWeather = weatherUiState.isLoading,
                        weatherError = weatherUiState.error,
                        locationViewModel = locationViewModel
                    )
                    content(innerPadding, getMonthName, weatherViewModel)
                }
            }
        }
    }
}
