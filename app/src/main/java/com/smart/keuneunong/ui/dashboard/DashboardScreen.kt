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
import com.smart.keuneunong.ui.theme.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smart.keuneunong.ui.components.CalendarComponent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Brush
import com.smart.keuneunong.ui.weather.WeatherScreen
import com.smart.keuneunong.ui.recommendation.RecommendationScreen
import com.smart.keuneunong.ui.notification.NotificationScreen

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    var selectedTab by remember { mutableStateOf<Int>(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        // Content based on selected tab
        when (selectedTab) {
            0 -> DashboardContent(uiState, viewModel, innerPadding)
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

@Composable
fun DashboardContent(
    uiState: DashboardUiState,
    viewModel: DashboardViewModel,
    contentPadding: PaddingValues
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        /** ---------- HEADER MODERN ---------- **/
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = greeting,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFE3F2FD)
                        )
                        Text(
                            text = "Smart Keuneunong",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${uiState.today.first} ${viewModel.getMonthName(uiState.today.second)} ${uiState.today.third}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFBBDEFB)
                        )
                    }

                    // Mini weather info
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Cloud,
                            contentDescription = "Cuaca",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Text("28°C", color = Color.White, style = MaterialTheme.typography.labelSmall)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { /* TODO: Settings */ },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.25f), shape = RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Pengaturan",
                            tint = Color.White
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
            CalendarComponent(
                currentMonth = uiState.currentMonth,
                currentYear = uiState.currentYear,
                calendarDays = uiState.calendarDays,
                onPreviousMonth = viewModel::onPreviousMonth,
                onNextMonth = viewModel::onNextMonth,
                getMonthName = viewModel::getMonthName
            )
        }

        /** ---------- FASE KEUNEUNONG ---------- **/
        item {
            FaseKeuneunongCard()
        }

        /** ---------- TENTANG ---------- **/
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(3.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "📖 Tentang Keuneunong",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Keuneunong adalah sistem kalender tradisional Aceh yang mengikuti fase bulan untuk menentukan waktu terbaik berbagai aktivitas seperti bercocok tanam, melaut, dan upacara adat.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF475569)
                    )
                }
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
