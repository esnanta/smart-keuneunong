package com.smart.keuneunong.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.ui.theme.*
import com.smart.keuneunong.utils.DateUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Text(
                text = "Smart Keuneunong",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
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

        // Feature Cards
        item {
            Text(
                text = "Fitur Utama",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Gray900
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    emoji = "📅",
                    title = "Kalender Keuneunong",
                    description = "Lihat fase bulan tradisional"
                )
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🌤️",
                    title = "Cuaca",
                    description = "Prakiraan cuaca terkini"
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🎣",
                    title = "Rekomendasi",
                    description = "Saran kegiatan harian"
                )
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    emoji = "📱",
                    title = "Notifikasi",
                    description = "Pengingat penting"
                )
            }
        }

        // Calendar Section
        item {
            CalendarCard(
                currentMonth = uiState.currentMonth,
                currentYear = uiState.currentYear,
                calendarDays = uiState.calendarDays,
                onPreviousMonth = viewModel::onPreviousMonth,
                onNextMonth = viewModel::onNextMonth,
                getMonthName = viewModel::getMonthName
            )
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
    }
}

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    emoji: String,
    title: String,
    description: String
) {
    Card(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = emoji,
                fontSize = 32.sp
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Gray900
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Gray500
            )
        }
    }
}

@Composable
fun CalendarCard(
    currentMonth: Int,
    currentYear: Int,
    calendarDays: List<CalendarDayData>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    getMonthName: (Int) -> String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Calendar Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPreviousMonth) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Previous month",
                        tint = Gray700
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${getMonthName(currentMonth)} $currentYear",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                    Text(
                        text = "Jumadil Awal 1447 H",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray500
                    )
                }

                IconButton(onClick = onNextMonth) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Next month",
                        tint = Gray700
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Day headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab").forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = Gray500,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Calendar Grid
            CalendarGrid(calendarDays)
        }
    }
}

@Composable
fun CalendarGrid(calendarDays: List<CalendarDayData>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        calendarDays.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { dayData ->
                    CalendarDayCell(
                        dayData = dayData,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarDayCell(
    dayData: CalendarDayData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        if (dayData.day > 0) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                // Day number with highlight for current day
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .then(
                            if (dayData.isToday) {
                                Modifier
                                    .background(Blue500, CircleShape)
                                    .shadow(4.dp, CircleShape)
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dayData.day.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = if (dayData.isToday) FontWeight.Bold else FontWeight.Normal,
                        color = if (dayData.isToday) Color.White else Gray900,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Weather icon
                Text(
                    text = dayData.weatherEmoji,
                    fontSize = 14.sp
                )

                // Special marker (checkmark for important days)
                if (dayData.hasSpecialEvent) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Special event",
                        tint = Green500,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
    }
}
