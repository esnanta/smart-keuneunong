package com.smart.keuneunong.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.outlined.MenuBook
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smart.keuneunong.data.model.WeatherData
import com.smart.keuneunong.ui.location.LocationViewModel
import java.util.Calendar
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ScreenHeader(
    currentDate: Triple<Int, Int, Int>, // day, month, year
    getMonthName: (Int) -> String,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
    weatherData: WeatherData? = null,
    isLoadingWeather: Boolean = false,
    weatherError: String? = null,
    locationViewModel: LocationViewModel? = null
) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greetingText = when (currentHour) {
        in 5..11 -> "Selamat Pagi"
        in 12..15 -> "Selamat Siang"
        in 16..18 -> "Selamat Sore"
        else -> "Selamat Malam"
    }

    // Get location display using the same logic as HomeScreen
    val locationState = locationViewModel?.selectedLocation?.collectAsStateWithLifecycle()?.value
    val locationDisplay = when (locationState) {
        is com.smart.keuneunong.ui.location.LocationState.Success -> {
            locationViewModel.getCityName(locationState.latitude, locationState.longitude)
        }
        else -> locationViewModel?.getCityName(5.1801, 97.1507) ?: weatherData?.location ?: ""
    }

    val activityIcon = getActivityIcons(weatherData?.condition).random()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF5B8DEF), Color(0xFF4E65D9))
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(horizontal = 20.dp, vertical = 28.dp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = greetingText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFE3F2FD),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = activityIcon,
                        contentDescription = "Aktivitas sesuai cuaca",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

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
                        text = "Keuneunong",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Kalender Tradisional Aceh",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFBBDEFB)
                    )

                    // Weather info from API
                    when {
                        isLoadingWeather -> {
                            LocationSkeleton(modifier = Modifier.padding(top = 4.dp))
                        }
                        weatherError != null -> {
                            Text(
                                text = "⚠️ $weatherError",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFFFCDD2),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        weatherData != null && locationDisplay.isNotEmpty() -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Lokasi",
                                    tint = Color(0xFFBBDEFB),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = locationDisplay,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFBBDEFB)
                                )
                            }
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    if (isLoadingWeather) {
                        // Show skeleton loading for weather data
                        WeatherSkeleton()
                    } else if (weatherData != null && weatherError == null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${weatherData.temperature}°C",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Icon(
                                imageVector = getWeatherIcon(weatherData.condition),
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Text(
                            text = weatherData.condition,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFBBDEFB),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "Terasa seperti ${weatherData.feelsLike}°C",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFBBDEFB),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    } else {
                        Icon(
                            imageVector = getWeatherIcon(null),
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun getWeatherIcon(condition: String?): ImageVector {
    return when (condition?.lowercase()) {
        "cerah", "sunny", "clear" -> Icons.Default.WbSunny
        "berawan", "cloudy", "overcast" -> Icons.Default.WbCloudy
        "hujan", "rain", "rainy", "drizzle", "shower" -> Icons.Default.Cloud
        "badai", "thunderstorm", "storm" -> Icons.Default.Thunderstorm
        else -> Icons.Default.Cloud
    }
}

private fun getActivityIcons(condition: String?): List<ImageVector> {
    return when (condition?.lowercase()) {
        "cerah", "sunny", "clear" -> listOf(
            Icons.AutoMirrored.Filled.DirectionsWalk,
            Icons.AutoMirrored.Filled.DirectionsBike,
            Icons.Filled.WbSunny
        )
        "berawan", "cloudy", "overcast" -> listOf(
            Icons.AutoMirrored.Outlined.MenuBook,
            Icons.Filled.WbCloudy,
            Icons.Filled.Cloud
        )
        "hujan", "rain", "rainy", "drizzle", "shower" -> listOf(
            Icons.Filled.Umbrella,
            Icons.Filled.Cloud,
            Icons.AutoMirrored.Outlined.MenuBook
        )
        "badai", "thunderstorm", "storm" -> listOf(
            Icons.Filled.Umbrella,
            Icons.Filled.Thunderstorm,
            Icons.Filled.Cloud
        )
        else -> listOf(
            Icons.AutoMirrored.Filled.DirectionsBike,
            Icons.AutoMirrored.Filled.DirectionsWalk,
            Icons.Filled.WbSunny
        )
    }
}
