package com.smart.keuneunong.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smart.keuneunong.ui.components.CalendarComponent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TipsAndUpdates
import com.smart.keuneunong.ui.components.QuickStatCard
import com.smart.keuneunong.ui.components.AppHeader
import com.smart.keuneunong.ui.weather.WeatherScreen
import com.smart.keuneunong.ui.recommendation.RecommendationScreen
import com.smart.keuneunong.ui.notification.NotificationScreen
import com.smart.keuneunong.ui.location.LocationPickerScreen
import com.smart.keuneunong.ui.location.LocationViewModel
import timber.log.Timber
import com.smart.keuneunong.ui.components.AppScaffold
import com.smart.keuneunong.ui.components.DrawerContent
import com.smart.keuneunong.ui.components.AboutDialog

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val locationState = locationViewModel.selectedLocation.collectAsStateWithLifecycle().value
    var selectedTab by remember { mutableStateOf<Int>(0) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showLocationPicker by remember { mutableStateOf(false) }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
    if (showLocationPicker) {
        LocationPickerScreen(
            onLocationSelected = { location ->
                locationViewModel.saveLocation(location.latitude, location.longitude)
                Timber.d("Location saved: Lat=${location.latitude}, Lng=${location.longitude}")
                showLocationPicker = false
            },
            onNavigateBack = {
                showLocationPicker = false
            }
        )
        return
    }

    AppScaffold(
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onLocationClick = {
                        showLocationPicker = true
                    },
                    onAboutClick = {
                        showAboutDialog = true
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding, openDrawer ->
        when (selectedTab) {
            0 -> DashboardContent(uiState, locationState, viewModel, innerPadding, openDrawer)
            1 -> WeatherScreen(innerPadding, openDrawer)
            2 -> RecommendationScreen(innerPadding, openDrawer)
            3 -> NotificationScreen(innerPadding, openDrawer)
        }
    }
}

@Composable
fun DashboardContent(
    uiState: DashboardUiState,
    locationState: com.smart.keuneunong.ui.location.LocationState,
    viewModel: DashboardViewModel,
    contentPadding: PaddingValues,
    onMenuClick: () -> Unit
) {

    // Get location display name
    val locationDisplay = when (locationState) {
        is com.smart.keuneunong.ui.location.LocationState.Success -> {
            getLocationName(locationState.latitude, locationState.longitude)
        }
        else -> "Lhokseumawe" // Default location (5.1801, 97.1507)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB))
            .padding(contentPadding),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AppHeader(
                title = "Smart Keuneunong",
                subtitle = viewModel.getFormattedToday(),
                showGreeting = true,
                onMenuClick = onMenuClick
            )
        }

        /** ---------- QUICK INFO CARDS ---------- **/
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickStatCard(
                    icon = com.smart.keuneunong.utils.DateUtils.getCurrentDay().toString(),
                    title = "Bulan",
                    value = com.smart.keuneunong.utils.DateUtils.getMonthName(com.smart.keuneunong.utils.DateUtils.getCurrentMonth()),
                    modifier = Modifier.weight(1f)
                )
                QuickStatCard(
                    icon = "🌕",
                    title = "Keuneunong",
                    value = "Muda",
                    modifier = Modifier.weight(1f)
                )
                QuickStatCard(
                    icon = "📍",
                    title = "Lokasi",
                    value = locationDisplay,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        /** ---------- KALENDER ---------- **/
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                CalendarComponent(
                    currentMonth = uiState.currentMonth,
                    currentYear = uiState.currentYear,
                    calendarDays = uiState.calendarDays,
                    onPreviousMonth = viewModel::onPreviousMonth,
                    onNextMonth = viewModel::onNextMonth,
                    getMonthName = viewModel::getMonthName
                )
            }
        }

        /** ---------- FASE KEUNEUNONG ---------- **/
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                FaseKeuneunongCard()
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Kalender",
                    tint = if (selectedTab == 0) Color(0xFF1976D2) else Color(0xFFB0BEC5)
                )
            },
            label = {
                Text(
                    text = "Kalender",
                    color = if (selectedTab == 0) Color(0xFF1976D2) else Color(0xFFB0BEC5),
                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = "Cuaca",
                    tint = if (selectedTab == 1) Color(0xFF1976D2) else Color(0xFFB0BEC5)
                )
            },
            label = {
                Text(
                    text = "Cuaca",
                    color = if (selectedTab == 1) Color(0xFF1976D2) else Color(0xFFB0BEC5),
                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            icon = {
                Icon(
                    imageVector = Icons.Default.TipsAndUpdates,
                    contentDescription = "Rekomendasi",
                    tint = if (selectedTab == 2) Color(0xFF1976D2) else Color(0xFFB0BEC5)
                )
            },
            label = {
                Text(
                    text = "Rekomendasi",
                    color = if (selectedTab == 2) Color(0xFF1976D2) else Color(0xFFB0BEC5),
                    fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifikasi",
                    tint = if (selectedTab == 3) Color(0xFF1976D2) else Color(0xFFB0BEC5)
                )
            },
            label = {
                Text(
                    text = "Notifikasi",
                    color = if (selectedTab == 3) Color(0xFF1976D2) else Color(0xFFB0BEC5),
                    fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
    }
}

@Composable
fun FaseKeuneunongCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Fase Keuneunong Bulan Ini",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF222B45)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Musim Tanam
            FaseInfoRow(
                icon = "\uD83C\uDF31", // 🌱
                title = "Musim Tanam",
                date = "8 November",
                description = "Waktu penanaman padi dan palawija"
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Musim Hujan
            FaseInfoRow(
                icon = "\uD83C\uDF27\uFE0F", // 🌧️
                title = "Musim Hujan",
                date = "15 November",
                description = "Curah hujan meningkat"
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Waktu Melaut
            FaseInfoRow(
                icon = "\uD83C\uDF0A", // 🌊
                title = "Waktu Melaut",
                date = "22 November",
                description = "Kondisi laut aman untuk nelayan"
            )
        }
    }
}

@Composable
fun FaseInfoRow(icon: String, title: String, date: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F7FA),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF222B45)
            )
            Text(
                text = "$date • $description",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}


/**
 * Get location name based on coordinates
 * Maps coordinates to known cities in Aceh or returns generic "Aceh"
 */
private fun getLocationName(latitude: Double, longitude: Double): String {
    val acehnesseCities = listOf(
        // Format: Triple(lat, lng, nama kota)
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

    // Toleransi jarak dalam derajat (sekitar 5-10 km)
    val tolerance = 0.1

    // Cari kota terdekat
    for ((cityLat, cityLng, cityName) in acehnesseCities) {
        val latDiff = kotlin.math.abs(latitude - cityLat)
        val lngDiff = kotlin.math.abs(longitude - cityLng)

        // Jika koordinat dekat dengan salah satu kota
        if (latDiff <= tolerance && lngDiff <= tolerance) {
            return cityName
        }
    }

    // Default "Aceh"
    return "Aceh"
}
