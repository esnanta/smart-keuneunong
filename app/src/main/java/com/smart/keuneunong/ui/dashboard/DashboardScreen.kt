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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.smart.keuneunong.ui.weather.WeatherScreen
import com.smart.keuneunong.ui.recommendation.RecommendationScreen
import com.smart.keuneunong.ui.notification.NotificationScreen

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    var selectedTab by remember { mutableStateOf<Int>(0) }
    var showAboutDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // About Dialog
    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onSettingsClick = {
                        scope.launch { drawerState.close() }
                        // TODO: Navigate to settings
                    },
                    onAboutClick = {
                        scope.launch { drawerState.close() }
                        showAboutDialog = true
                    }
                )
            }
        }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets.systemBars,
            bottomBar = {
                BottomNavigationBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        ) { innerPadding ->
            // Content based on selected tab
            when (selectedTab) {
                0 -> DashboardContent(uiState, viewModel, innerPadding) {
                    scope.launch { drawerState.open() }
                }
                1 -> Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) { WeatherScreen() }
                2 -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) { RecommendationScreen() }

                3 -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) { NotificationScreen() }
            }
        }
    }
}

@Composable
fun DashboardContent(
    uiState: DashboardUiState,
    viewModel: DashboardViewModel,
    contentPadding: PaddingValues,
    onMenuClick: () -> Unit
) {
    val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 5..11 -> "Selamat Pagi ☀️"
        in 12..15 -> "Selamat Siang 🌤️"
        in 16..18 -> "Selamat Sore 🌇"
        else -> "Selamat Malam 🌙"
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF5B8DEF), Color(0xFF4E65D9))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Memberi jarak antar baris
                ) {
                    // BARIS 1: Greeting dan Tombol Menu sejajar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = greeting,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFFE3F2FD),
                            fontWeight = FontWeight.Medium
                        )

                        IconButton(
                            onClick = onMenuClick,
                            modifier = Modifier
                                .size(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    }

                    // PERBAIKAN: BARIS 2 (Icon cuaca dan suhu) dihapus sesuai permintaan.
                    // Judul sekarang langsung di bawah baris 1.

                    // BARIS 2 (Sebelumnya BARIS 3): Judul Smart Keuneunong dan icon cuaca kanan
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Smart Keuneunong",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${uiState.today.first} ${viewModel.getMonthName(uiState.today.second)} ${uiState.today.third}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFBBDEFB)
                            )
                        }

                        // Icon cuaca kecil di kanan
                        Icon(
                            imageVector = Icons.Default.Cloud,
                            contentDescription = null, // Sesuai permintaan: icon cuaca di kanan
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        /** ---------- QUICK INFO CARDS ---------- **/
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickStatCard(icon = "📅", title = "Bulan", value = viewModel.getMonthName(uiState.currentMonth), modifier = Modifier.weight(1f))
                QuickStatCard(icon = "🌕", title = "Keuneunong", value = "Muda", modifier = Modifier.weight(1f))
                QuickStatCard(icon = "📍", title = "Lokasi", value = "Aceh", modifier = Modifier.weight(1f))
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
fun QuickStatCard(icon: String, title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .shadow(2.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                color = Color(0xFF64748B),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = value,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B),
                style = MaterialTheme.typography.titleSmall
            )
        }
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

@Composable
fun DrawerContent(
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Smart Keuneunong",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Kalender Tradisional Aceh",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Pengaturan") },
            selected = false,
            onClick = onSettingsClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            label = { Text("Tentang Aplikasi") },
            selected = false,
            onClick = onAboutClick
        )
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
