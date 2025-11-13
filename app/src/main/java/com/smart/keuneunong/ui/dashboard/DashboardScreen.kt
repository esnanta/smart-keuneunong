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

@Composable
fun DashboardScreen() {
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
                                    text = "${DateUtils.getCurrentDay()} ${DateUtils.getMonthName(DateUtils.getCurrentMonth())} ${DateUtils.getCurrentYear()}",
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
            CalendarCard()
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
fun CalendarCard() {
    var currentMonth by remember { mutableStateOf(11) } // November
    var currentYear by remember { mutableStateOf(2025) }

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
                IconButton(
                    onClick = {
                        if (currentMonth == 1) {
                            currentMonth = 12
                            currentYear--
                        } else {
                            currentMonth--
                        }
                    }
                ) {
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

                IconButton(
                    onClick = {
                        if (currentMonth == 12) {
                            currentMonth = 1
                            currentYear++
                        } else {
                            currentMonth++
                        }
                    }
                ) {
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
            CalendarGrid(currentMonth, currentYear)
        }
    }
}

@Composable
fun CalendarGrid(month: Int, year: Int) {
    val calendarDays = getCalendarDays(month, year)

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

fun getCalendarDays(month: Int, year: Int): List<CalendarDayData> {
    val days = mutableListOf<CalendarDayData>()

    // Get first day of month (0 = Sunday, 1 = Monday, etc.)
    val firstDayOfWeek = getFirstDayOfMonth(month, year)

    // Get number of days in month
    val daysInMonth = getDaysInMonth(month, year)

    // Add empty cells for days before month starts
    repeat(firstDayOfWeek) {
        days.add(CalendarDayData(day = 0))
    }

    // Add days of the month with sample weather data
    for (day in 1..daysInMonth) {
        val isToday = (day == 8 && month == 11 && year == 2025) // November 8 as shown in image
        val weatherEmoji = when {
            day in listOf(3, 10, 17, 24) -> "🌧️"
            day in listOf(2, 7, 9, 11, 14, 18, 21, 25, 27) -> "⛅"
            day in listOf(16, 23, 29) -> "☁️"
            else -> "☀️"
        }
        val hasSpecialEvent = day in listOf(15, 22)

        days.add(
            CalendarDayData(
                day = day,
                isToday = isToday,
                weatherEmoji = weatherEmoji,
                hasSpecialEvent = hasSpecialEvent
            )
        )
    }

    // Add empty cells to complete the last week (make sure we have full rows of 7)
    val totalCells = days.size
    val remainingCells = if (totalCells % 7 != 0) 7 - (totalCells % 7) else 0
    repeat(remainingCells) {
        days.add(CalendarDayData(day = 0))
    }

    return days
}

fun getFirstDayOfMonth(month: Int, year: Int): Int {
    // Simplified calculation - returns day of week (0-6, Sunday-Saturday)
    // For November 2025, it starts on Saturday (6)
    if (month == 11 && year == 2025) return 6

    // Basic Zeller's congruence for other dates
    val m = if (month < 3) month + 12 else month
    val y = if (month < 3) year - 1 else year
    val k = y % 100
    val j = y / 100
    val h = (1 + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7
    return (h + 6) % 7 // Convert to 0=Sunday format
}

fun getDaysInMonth(month: Int, year: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 30
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "Januari"
        2 -> "Februari"
        3 -> "Maret"
        4 -> "April"
        5 -> "Mei"
        6 -> "Juni"
        7 -> "Juli"
        8 -> "Agustus"
        9 -> "September"
        10 -> "Oktober"
        11 -> "November"
        12 -> "Desember"
        else -> "Unknown"
    }
}

