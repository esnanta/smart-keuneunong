package com.smart.keuneunong.ui.home

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
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TipsAndUpdates
import com.smart.keuneunong.ui.components.QuickStatCard
import com.smart.keuneunong.ui.components.ScreenWithHeaderAndDrawer
import com.smart.keuneunong.ui.weather.WeatherScreen
import com.smart.keuneunong.ui.recommendation.RecommendationScreen
import com.smart.keuneunong.ui.notification.NotificationScreen
import com.smart.keuneunong.ui.location.LocationViewModel
import com.smart.keuneunong.domain.repository.RepositoryKeuneunong

@Composable
fun HomeScreen(
    locationViewModel: LocationViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val homeViewModel: HomeViewModel = hiltViewModel()
    val repositoryKeuneunong = homeViewModel.repositoryKeuneunong

    ScreenWithHeaderAndDrawer(locationViewModel = locationViewModel) { innerPadding, getMonthName ->
        Scaffold(
            contentWindowInsets = WindowInsets.systemBars,
            bottomBar = {
                BottomNavigationBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        ) { scaffoldPadding ->
            Box(modifier = Modifier.padding(scaffoldPadding)) {
                when (selectedTab) {
                    0 -> HomeContent(
                        viewModel = homeViewModel,
                        locationViewModel = locationViewModel,
                        repositoryKeuneunong = repositoryKeuneunong,
                        contentPadding = innerPadding
                    )
                    1 -> WeatherScreen(contentPadding = innerPadding)
                    2 -> RecommendationScreen(contentPadding = innerPadding)
                    3 -> NotificationScreen(contentPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    locationViewModel: LocationViewModel,
    repositoryKeuneunong: RepositoryKeuneunong,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val locationState = locationViewModel.selectedLocation.collectAsStateWithLifecycle().value
    val locationDisplay = when (locationState) {
        is com.smart.keuneunong.ui.location.LocationState.Success -> {
            locationViewModel.getCityName(locationState.latitude, locationState.longitude)
        }
        else -> locationViewModel.getCityName(5.1801, 97.1507)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB))
            .padding(contentPadding),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        /** ---------- QUICK INFO CARDS ---------- **/
        item {
            val hijriDate = remember { com.smart.keuneunong.utils.DateUtils.getCurrentHijriDate() }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickStatCard(
                    icon = hijriDate.day.toString(),
                    title = "Uroe Buleun",
                    value = com.smart.keuneunong.utils.DateUtils.getHijriMonthName(hijriDate.month),
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
                KeuneunongPhaseCard(phases = repositoryKeuneunong.getPhases())
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
                    contentDescription = "Saran",
                    tint = if (selectedTab == 2) Color(0xFF1976D2) else Color(0xFFB0BEC5)
                )
            },
            label = {
                Text(
                    text = "Saran",
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
fun KeuneunongPhaseCard(phases: List<com.smart.keuneunong.data.model.KeuneunongPhase>) {
    val dateFormatter = remember { java.text.SimpleDateFormat("dd MMMM", Locale.getDefault()) }

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
            phases.forEach { phase ->
                FaseInfoRow(
                    icon = phase.icon,
                    title = phase.name,
                    date = "${dateFormatter.format(java.util.Date(phase.startDate))} - ${dateFormatter.format(java.util.Date(phase.endDate))}",
                    description = phase.description + if (phase.activities.isNotEmpty()) "\nAktivitas: ${phase.activities.joinToString(", ")}" else ""
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
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


@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup", color = Color(0xFF1976D2))
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📖",
                    fontSize = 24.sp
                )
                Text(
                    text = "Tentang Keuneunong",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Keuneunong adalah sistem kalender tradisional Aceh yang mengikuti fase bulan untuk menentukan waktu terbaik berbagai aktivitas seperti bercocok tanam, melaut, dan upacara adat.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF475569)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Fitur Aplikasi:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "• Kalender berbasis fase bulan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569)
                    )
                    Text(
                        text = "• Informasi cuaca terkini",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569)
                    )
                    Text(
                        text = "• Rekomendasi aktivitas pertanian",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569)
                    )
                    Text(
                        text = "• Notifikasi fase bulan penting",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569)
                    )
                }
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White
    )
}


