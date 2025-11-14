package com.smart.keuneunong.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.smart.keuneunong.ui.components.AboutDialog
import com.smart.keuneunong.ui.components.AppScaffold
import com.smart.keuneunong.ui.components.DrawerContent
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerScreen(
    onLocationSelected: (LatLng) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAboutDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var uiState by remember { mutableStateOf(LocationUIState()) }
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Lhokseumawe, Aceh
    val defaultLocation = LatLng(5.1801, 97.1507)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            uiState.selectedLocation ?: defaultLocation,
            15f
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        uiState = uiState.copy(
            hasLocationPermission = granted,
            errorMessage = if (!granted) "Izin lokasi diperlukan untuk menggunakan fitur ini" else null
        )
    }

    LaunchedEffect(Unit) {
        uiState = uiState.copy(
            hasLocationPermission = checkLocationPermission(context)
        )

        if (!uiState.hasLocationPermission) {
            timber.log.Timber.d("LocationPicker: Requesting location permission")
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            timber.log.Timber.d("LocationPicker: Location permission already granted")
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }

    AppScaffold(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onLocationClick = { /* Implement if needed */ },
                    onAboutClick = { showAboutDialog = true }
                )
            }
        },
        topBar = { openDrawer ->
            TopAppBar(
                title = { Text("Pilih Lokasi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5B8DEF),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues, openDrawer ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!uiState.hasLocationPermission) {
                PermissionDeniedContent(
                    onRequestPermission = {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                )
            } else {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = uiState.hasLocationPermission,
                        // Optimize map loading
                        isTrafficEnabled = false,
                        isIndoorEnabled = false,
                        isBuildingEnabled = true,
                        minZoomPreference = 5f,
                        maxZoomPreference = 20f
                    ),
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = true,
                        myLocationButtonEnabled = false,
                        compassEnabled = true,
                        rotationGesturesEnabled = false,
                        tiltGesturesEnabled = false,
                        // Reduce unnecessary map features
                        mapToolbarEnabled = false
                    ),
                    onMapClick = { latLng ->
                        uiState = uiState.copy(selectedLocation = latLng)
                    }
                ) {
                    uiState.selectedLocation?.let { location ->
                        Marker(
                            state = MarkerState(position = location),
                            title = "Lokasi Terpilih",
                            snippet = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                        )
                    }
                }

                if (uiState.selectedLocation != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Lokasi Terpilih",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Latitude: ${String.format(Locale.US, "%.6f", uiState.selectedLocation?.latitude)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Longitude: ${String.format(Locale.US, "%.6f", uiState.selectedLocation?.longitude)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                uiState.errorMessage?.let { message ->
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopCenter),
                        action = {
                            TextButton(onClick = {
                                uiState = uiState.copy(errorMessage = null)
                            }) {
                                Text("Tutup")
                            }
                        }
                    ) {
                        Text(message)
                    }
                }
            }
        }
    }
}

@Composable
private fun PermissionDeniedContent(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.MyLocation,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Izin Lokasi Diperlukan",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Aplikasi memerlukan izin akses lokasi untuk dapat menampilkan peta dan menentukan lokasi Anda saat ini.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Minta Izin Lokasi")
        }
    }
}

private fun checkLocationPermission(context: Context): Boolean {
    return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED ||
            context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
private suspend fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient
): LatLng? = suspendCoroutine { continuation ->
    try {
        timber.log.Timber.d("LocationPicker: Requesting current location")
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            if (location != null) {
                timber.log.Timber.d("LocationPicker: Location received - Lat: ${location.latitude}, Lng: ${location.longitude}")
                continuation.resume(LatLng(location.latitude, location.longitude))
            } else {
                timber.log.Timber.w("LocationPicker: Location is null")
                continuation.resume(null)
            }
        }.addOnFailureListener { exception ->
            timber.log.Timber.e(exception, "LocationPicker: Failed to get location")
            continuation.resume(null)
        }
    } catch (e: Exception) {
        timber.log.Timber.e(e, "LocationPicker: Exception in getCurrentLocation")
        continuation.resume(null)
    }
}
