package com.smart.keuneunong.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smart.keuneunong.data.model.WeatherData
import com.smart.keuneunong.ui.theme.Blue50
import com.smart.keuneunong.ui.theme.Blue500
import com.smart.keuneunong.ui.theme.Blue800
import com.smart.keuneunong.ui.theme.Blue900
import com.smart.keuneunong.ui.theme.Gray50
import com.smart.keuneunong.ui.theme.Gray500
import com.smart.keuneunong.ui.theme.Gray600
import com.smart.keuneunong.ui.theme.Gray700
import com.smart.keuneunong.ui.theme.Gray900
import com.smart.keuneunong.ui.theme.SmartKeuneunongTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error!!, color = Color.Red)
            }
        } else if (uiState.weatherData != null) {
            WeatherContent(uiState.weatherData!!)
        }
    }
}

@Composable
fun WeatherContent(weatherData: WeatherData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Weather Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = weatherData.weatherIcon, fontSize = 48.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "${weatherData.temperature}°C",
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                        color = Gray900
                    )
                    Text(
                        text = "Terasa seperti ${weatherData.feelsLike}°C",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Gray600
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = weatherData.location,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Gray700
                )
                Text(
                    text = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(Date(weatherData.date)),
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray500
                )
                Text(
                    text = weatherData.condition,
                    style = MaterialTheme.typography.titleMedium,
                    color = Gray500
                )
            }
        }

        // Konteks Keuneunong
        KeuneunongContext(contextText = weatherData.keuneunongContext)

        // Detail Hari Ini
        DailyDetails(
            windSpeed = "${weatherData.windSpeed} km/j",
            humidity = "${weatherData.humidity}%",
            sunrise = weatherData.sunrise,
            sunset = weatherData.sunset
        )

        // Prakiraan Laut
        SeaForecast(
            waveHeightMin = weatherData.waveHeightMin,
            waveHeightMax = weatherData.waveHeightMax,
            windSpeed = "${weatherData.seaWindSpeed} km/j",
            windDirection = weatherData.seaWindDirection,
            summary = weatherData.seaConditionSummary
        )
    }
}

@Composable
fun KeuneunongContext(contextText: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Blue50)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Konteks Keuneunong",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Blue800
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = contextText,
                style = MaterialTheme.typography.bodyMedium,
                color = Blue900
            )
        }
    }
}

@Composable
fun DailyDetails(windSpeed: String, humidity: String, sunrise: String, sunset: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Detail Hari Ini",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetailItem(icon = Icons.Default.Air, label = "Angin", value = windSpeed)
            WeatherDetailItem(icon = Icons.Default.WaterDrop, label = "Kelembapan", value = humidity)
            WeatherDetailItem(icon = Icons.Default.WbSunny, label = "Terbit", value = sunrise)
            WeatherDetailItem(icon = Icons.Default.NightsStay, label = "Terbenam", value = sunset)
        }
    }
}

@Composable
fun SeaForecast(waveHeightMin: Double, waveHeightMax: Double, windSpeed: String, windDirection: String, summary: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Prakiraan Laut",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SeaDetailItem(icon = Icons.Default.Waves, label = "Tinggi Gelombang", value = "$waveHeightMin - $waveHeightMax m")
                    SeaDetailItem(icon = Icons.Default.Air, label = "Angin di Laut", value = "$windSpeed ($windDirection)")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun WeatherDetailItem(icon: ImageVector, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.width(80.dp)
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Gray700, modifier = Modifier.size(28.dp))
        Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Gray900)
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Gray500)
    }
}

@Composable
fun SeaDetailItem(icon: ImageVector, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.width(120.dp)
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Blue500, modifier = Modifier.size(32.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Blue800,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Gray600,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    SmartKeuneunongTheme {
        WeatherContent(
            weatherData = WeatherData(
                weatherIcon = "☀️",
                temperature = 30,
                location = "Banda Aceh",
                condition = "Cerah",
                keuneunongContext = "Masa keuneunong telah tiba, persiapkan diri untuk turun ke laut. Angin bertiup sedang, cocok untuk perahu kecil.",
                windSpeed = 15,
                humidity = 75,
                sunrise = "06:30",
                sunset = "18:45",
                waveHeightMin = 0.5,
                waveHeightMax = 1.2,
                seaWindSpeed = 20,
                seaWindDirection = "Barat",
                seaConditionSummary = "Gelombang tenang, kondisi baik untuk melaut.",
                feelsLike = 32,
                date = System.currentTimeMillis()
            )
        )
    }
}
