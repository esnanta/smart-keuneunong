package com.smart.keuneunong.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.keuneunong.ui.components.DashboardHeader
import com.smart.keuneunong.ui.theme.*
import java.util.Calendar

@Composable
fun WeatherScreen(
    onMenuClick: () -> Unit = {}
) {
    val calendar = Calendar.getInstance()
    val today = Triple(
        calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.YEAR)
    )
    val monthNames = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )
    val getMonthName: (Int) -> String = { month ->
        monthNames.getOrElse(month - 1) { "" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .verticalScroll(rememberScrollState())
    ) {
        DashboardHeader(
            currentDate = today,
            getMonthName = getMonthName,
            onMenuClick = onMenuClick
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Judul Halaman
        Text(
            text = "Prakiraan Cuaca",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )

        // 1. Ringkasan Cuaca Saat Ini
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Banda Aceh",
                style = MaterialTheme.typography.headlineSmall,
                color = Gray700
            )
            // Ikon cuaca besar
            Text(
                text = "🌤️", // Anda bisa ganti dengan Image/Icon
                fontSize = 80.sp
            )
            Text(
                text = "29°C",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
            Text(
                text = "Cerah Berawan",
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
                    text = "Saat ini: Keuneunong Ròt (Musim Tanam)",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Gray700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cuaca cerah berawan sangat baik untuk memulai pengolahan sawah dan penanaman padi. Waspadai hujan singkat di sore hari.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Detail Hari Ini
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Detail Hari Ini",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Gray900,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                // Grid 2x2 untuk detail
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDetailItem(
                        icon = Icons.Default.Air,
                        label = "Angin",
                        value = "14 km/j"
                    )
                    WeatherDetailItem(
                        icon = Icons.Default.WaterDrop,
                        label = "Kelembapan",
                        value = "72%"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDetailItem(
                        icon = Icons.Default.WbSunny,
                        label = "Terbit",
                        value = "06:18"
                    )
                    WeatherDetailItem(
                        icon = Icons.Default.Brightness4,
                        label = "Terbenam",
                        value = "18:45"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Prakiraan Laut
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Prakiraan Laut",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Gray900,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WeatherDetailItem(
                        icon = Icons.Default.Waves,
                        label = "Tinggi Gelombang",
                        value = "0.5 - 1.2 m"
                    )
                    WeatherDetailItem(
                        icon = Icons.Default.WindPower,
                        label = "Angin di Laut",
                        value = "18 km/j (Barat)"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Kondisi laut cukup tenang, aman untuk aktivitas melaut. Tetap waspada terhadap perubahan cuaca tiba-tiba.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun WeatherDetailItem(icon: ImageVector, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Gray700,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Gray500
        )
    }
}
