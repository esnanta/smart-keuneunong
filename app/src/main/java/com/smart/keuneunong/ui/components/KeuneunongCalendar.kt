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
import com.smart.keuneunong.domain.model.RainfallCategory
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

            Spacer(modifier = Modifier.height(16.dp))

            // Rainfall Category Legend
            RainfallLegend()
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

                if (dayData.weatherEmoji != null) {
                    Text(
                        text = dayData.weatherEmoji,
                        fontSize = 14.sp,
                        modifier = Modifier.height(16.dp) // Maintain consistent height
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp)) // Placeholder to maintain alignment
                }

                Spacer(modifier = Modifier.height(4.dp))

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

                Spacer(modifier = Modifier.height(4.dp))

                // Rainfall category label (colored circle/strip)
                if (dayData.rainfallCategory != null) {
                    Box(
                        modifier = Modifier
                            .size(width = 24.dp, height = 6.dp)
                            .background(
                                color = getRainfallColor(dayData.rainfallCategory),
                                shape = RoundedCornerShape(3.dp)
                            )
                    )
                } else {
                    // Placeholder to maintain spacing
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Special marker (checkmark for important days)
                if (dayData.hasSpecialEvent) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Special event",
                        tint = Green500,
                        modifier = Modifier.size(10.dp)
                    )
                } else {
                    // Placeholder to maintain spacing
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

/**
 * Helper function untuk mendapatkan warna berdasarkan kategori curah hujan
 */
@Composable
private fun getRainfallColor(category: String?): Color {
    return when (category?.let { RainfallCategory.valueOf(it) }) {
        RainfallCategory.TINGGI -> RainfallHigh
        RainfallCategory.SEDANG -> RainfallMedium
        RainfallCategory.RENDAH -> RainfallLow
        RainfallCategory.SANGAT_RENDAH -> RainfallVeryLow
        null -> Color.Transparent
    }
}

/**
 * Legend component untuk menampilkan kategori curah hujan
 */
@Composable
fun RainfallLegend() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Kategori Curah Hujan",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = Gray700,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem(
                color = RainfallHigh,
                label = "Tinggi",
                modifier = Modifier.weight(1f)
            )
            LegendItem(
                color = RainfallMedium,
                label = "Sedang",
                modifier = Modifier.weight(1f)
            )
            LegendItem(
                color = RainfallLow,
                label = "Rendah",
                modifier = Modifier.weight(1f)
            )
            LegendItem(
                color = RainfallVeryLow,
                label = "S. Rendah",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Single legend item component
 */
@Composable
fun LegendItem(
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 16.dp, height = 6.dp)
                .background(color, RoundedCornerShape(3.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Gray500,
            fontSize = 10.sp
        )
    }
}

