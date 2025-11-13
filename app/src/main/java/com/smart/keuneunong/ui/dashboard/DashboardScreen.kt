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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(contentPadding)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        // Header modern dengan background gradient & greeting
        item {
            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF64B5F6), Color(0xFF1976D2))
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(brush = gradientBrush)
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Selamat datang 👋",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE3F2FD)
                            )
                            Text(
                                text = "Smart Keuneunong",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${uiState.today.first} ${viewModel.getMonthName(uiState.today.second)} ${uiState.today.third}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFBBDEFB)
                            )
                        }

                        IconButton(
                            onClick = { /* TODO: Settings Action */ },
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
        }


        // Welcome Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Blue500
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "🌙",
                            fontSize = 48.sp
                        )
                        Text(
                            text = "Selamat Datang di",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Blue100
                        )
                        Text(
                            text = "Smart Keuneunong",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Kalender Cerdas Tradisional Aceh",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Blue100
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Current date info
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Hari ini",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Blue100
                                )
                                Text(
                                    text = "${uiState.today.first} ${viewModel.getMonthName(uiState.today.second)} ${uiState.today.third}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Fase: Keuneunong Muda 🌙",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Blue100
                                )
                            }
                        }
                    }
                }
            }
        }

        // Calendar Section
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


        // Fase Keuneunong Card
        item {
            FaseKeuneunongCard()
        }

        // Info Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Green50
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "📚 Tentang Keuneunong",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Green700
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Keuneunong adalah sistem kalender tradisional Aceh yang mengikuti fase bulan untuk menentukan waktu terbaik berbagai aktivitas seperti bercocok tanam, melaut, dan upacara adat.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Green600
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(1.dp))
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
