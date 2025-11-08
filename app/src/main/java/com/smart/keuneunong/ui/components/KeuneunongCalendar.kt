package com.smart.keuneunong.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.keuneunong.data.model.KeuneunongPhase
import com.smart.keuneunong.ui.theme.*
import com.smart.keuneunong.utils.DateUtils

@Composable
fun KeuneunongCalendar(
    currentMonth: Int,
    currentYear: Int,
    onMonthChange: (Int) -> Unit,
    phases: List<KeuneunongPhase>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${DateUtils.getMonthName(currentMonth)} $currentYear",
                        style = MaterialTheme.typography.titleLarge,
                        color = Gray900
                    )
                    Text(
                        text = "Jumadil Awal 1447 H",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray500
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = { onMonthChange(-1) }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Bulan sebelumnya"
                        )
                    }
                    IconButton(onClick = { onMonthChange(1) }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Bulan berikutnya"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Days of week
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

            // Calendar grid
            CalendarGrid(
                daysInMonth = DateUtils.getDaysInMonth(currentYear, currentMonth),
                startDayOfWeek = DateUtils.getStartDayOfWeek(currentYear, currentMonth),
                currentDay = if (currentMonth == DateUtils.getCurrentMonth() && currentYear == DateUtils.getCurrentYear()) DateUtils.getCurrentDay() else -1,
                phases = phases
            )
        }
    }
}

@Composable
fun CalendarGrid(
    daysInMonth: Int,
    startDayOfWeek: Int,
    currentDay: Int,
    phases: List<KeuneunongPhase>
) {
    val totalCells = ((daysInMonth + startDayOfWeek - 1) / 7 + 1) * 7

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.height(300.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(totalCells) { index ->
            val day = index - startDayOfWeek + 2 // Adjust for 1-based day
            if (day in 1..daysInMonth) {
                CalendarDay(
                    day = day,
                    isToday = day == currentDay,
                    hasPhase = phases.any { it.startDate == day.toLong() },
                    weatherIcon = DateUtils.getWeatherIconForDay(day)
                )
            } else {
                Spacer(modifier = Modifier.aspectRatio(1f))
            }
        }
    }
}

@Composable
fun CalendarDay(
    day: Int,
    isToday: Boolean,
    hasPhase: Boolean,
    weatherIcon: String
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(
                when {
                    isToday -> Blue500
                    hasPhase -> Green50
                    else -> Color.Transparent
                }
            )
            .clickable { /* Handle day click */ },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
                color = if (isToday) Color.White else Gray700
            )
            Text(
                text = weatherIcon,
                fontSize = 14.sp
            )
        }
    }
}
