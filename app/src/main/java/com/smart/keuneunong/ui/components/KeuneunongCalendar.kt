package com.smart.keuneunong.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.keuneunong.data.model.CalendarDayData
import com.smart.keuneunong.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun CalendarComponent(
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

