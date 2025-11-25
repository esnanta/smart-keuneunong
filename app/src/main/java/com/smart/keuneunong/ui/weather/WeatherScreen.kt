package com.smart.keuneunong.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smart.keuneunong.data.model.WeatherData
import com.smart.keuneunong.ui.theme.Gray50
import com.smart.keuneunong.ui.theme.Gray500
import com.smart.keuneunong.ui.theme.Gray700
import com.smart.keuneunong.ui.theme.Gray900
import com.smart.keuneunong.ui.theme.SmartKeuneunongTheme

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
        Spacer(modifier = Modifier.height(16.dp))

        // Judul Halaman
        Text(
            text = "Prakiraan Cuaca",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error!!)
            }
        } else if (uiState.weatherData != null) {
            WeatherContent(uiState.weatherData!!)
        }
    }
}

@Composable
fun WeatherContent(weatherData: WeatherData) {
    // 1. Ringkasan Cuaca Saat Ini
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = weatherData.location,
            style = MaterialTheme.typography.headlineSmall,
            color = Gray700
        )
        // Ikon cuaca besar
        Text(
            text = weatherData.weatherIcon, // Anda bisa ganti dengan Image/Icon
            fontSize = 80.sp
        )
        Text(
            text = "${weatherData.temperature}°C",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900
        )
        Text(
            text = weatherData.condition,
            style = MaterialTheme.typography.titleLarge,
            color = Gray500
        )
    }

    // 2. Detail Cuaca
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Detail Cuaca",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetailItem(icon = Icons.Default.Air, label = "Angin", value = "${weatherData.windSpeed} km/j")
            WeatherDetailItem(icon = Icons.Default.WaterDrop, label = "Kelembapan", value = "${weatherData.humidity}%")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetailItem(icon = Icons.Default.WbSunny, label = "Feels Like", value = "${weatherData.feelsLike}°C")
            WeatherDetailItem(
                icon = Icons.Default.CalendarToday,
                label = "Tanggal",
                value = java.text.SimpleDateFormat("dd MMM yyyy").format(java.util.Date(weatherData.date))
            )
        }
    }
}

@Composable
fun WeatherDetailItem(icon: ImageVector, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Gray500)
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Gray500)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    SmartKeuneunongTheme {
        WeatherScreen()
    }
}
