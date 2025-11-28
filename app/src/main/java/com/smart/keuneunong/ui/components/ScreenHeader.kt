package com.smart.keuneunong.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smart.keuneunong.data.model.WeatherData
import java.util.Calendar

@Composable
fun ScreenHeader(
    currentDate: Triple<Int, Int, Int>, // day, month, year
    getMonthName: (Int) -> String,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
    weatherData: WeatherData? = null,
    isLoadingWeather: Boolean = false,
    weatherError: String? = null
) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 5..11 -> "Selamat Pagi ☀️"
        in 12..15 -> "Selamat Siang 🌤️"
        in 16..18 -> "Selamat Sore 🌇"
        else -> "Selamat Malam 🌙"
    }

    Box(
        modifier = modifier
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            }

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
                        text = "${currentDate.first} ${getMonthName(currentDate.second)} ${currentDate.third}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFBBDEFB)
                    )

                    // Weather info from API
                    when {
                        isLoadingWeather -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(12.dp),
                                    color = Color.White,
                                    strokeWidth = 1.5.dp
                                )
                                Text(
                                    text = "Memuat cuaca...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFBBDEFB)
                                )
                            }
                        }
                        weatherError != null -> {
                            Text(
                                text = "⚠️ $weatherError",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFFFCDD2),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        weatherData != null -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = "${weatherData.temperature}°C",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFBBDEFB)
                                )
                                Text(
                                    text = weatherData.condition,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFBBDEFB)
                                )
                            }
                            if (weatherData.location.isNotEmpty()) {
                                Text(
                                    text = "📍 ${weatherData.location}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFBBDEFB),
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

