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
        verticalArrangement = Arrangement.spacedBy(24.dp) // Increased space
    ) {

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
                text = "Keuneunong",
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
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoRow(icon = Icons.Default.Air, label = "Angin", value = windSpeed)
                InfoRow(icon = Icons.Default.WaterDrop, label = "Kelembapan", value = humidity)
                InfoRow(icon = Icons.Default.WbSunny, label = "Terbit", value = sunrise)
                InfoRow(icon = Icons.Default.NightsStay, label = "Terbenam", value = sunset)
            }
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
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoRow(icon = Icons.Default.Waves, label = "Tinggi Gelombang", value = "$waveHeightMin - $waveHeightMax m")
                InfoRow(icon = Icons.Default.Air, label = "Angin di Laut", value = "$windSpeed ($windDirection)")

                Divider(modifier = Modifier.padding(vertical = 8.dp))

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
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Blue800,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Gray700
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Gray900
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    SmartKeuneunongTheme {
        Column(modifier = Modifier.background(Gray50)) {
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
}
