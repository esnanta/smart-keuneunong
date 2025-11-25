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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smart.keuneunong.ui.theme.Gray50
import com.smart.keuneunong.ui.theme.Gray500
import com.smart.keuneunong.ui.theme.Gray700
import com.smart.keuneunong.ui.theme.Gray900
import com.smart.keuneunong.ui.theme.SmartKeuneunongTheme
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.smart.keuneunong.ui.weather.WeatherUiState.Loading
import com.smart.keuneunong.ui.weather.WeatherUiState.Success
import com.smart.keuneunong.ui.weather.WeatherUiState.Error

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

        when (val state = uiState) {
            is Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Success -> {
                WeatherContent(state.weatherData)
            }
            is Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message)
                }
            }
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

    // 2. Konteks Keuneunong
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Biru muda
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Konteks Keuneunong",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherData.keuneunongContext,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Gray700
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = weatherData.keuneunongDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = Gray700
            )
        }
    }

    // 3. Prakiraan Per Jam
    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        Text(
            text = "Prakiraan Per Jam",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(weatherData.hourlyForecast) { forecast ->
                HourlyForecastItem(forecast)
            }
        }
    }

    // 4. Prakiraan 5 Hari
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Prakiraan 5 Hari",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        weatherData.dailyForecast.forEach { forecast ->
            DailyForecastItem(forecast)
        }
    }

    // 5. Detail Cuaca
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
            WeatherDetailItem(icon = Icons.Default.Air, label = "Angin", value = weatherData.wind)
            WeatherDetailItem(icon = Icons.Default.WaterDrop, label = "Kelembapan", value = weatherData.humidity)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetailItem(icon = Icons.Default.WbSunny, label = "Indeks UV", value = weatherData.uvIndex)
            WeatherDetailItem(
                icon = Icons.Default.Compress,
                label = "Tekanan",
                value = weatherData.pressure
            )
        }
    }
}

@Composable
fun HourlyForecastItem(forecast: HourlyForecast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = forecast.time, style = MaterialTheme.typography.bodySmall, color = Gray500)
        Text(text = forecast.icon, fontSize = 24.sp)
        Text(
            text = "${forecast.temperature}°",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DailyForecastItem(forecast: DailyForecast) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = forecast.day,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(text = forecast.icon, fontSize = 24.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${forecast.tempMax}°",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${forecast.tempMin}°",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray500
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
