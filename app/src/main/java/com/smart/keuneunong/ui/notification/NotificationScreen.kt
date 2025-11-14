package com.smart.keuneunong.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smart.keuneunong.ui.theme.*


data class NotificationData(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: String,
    val icon: ImageVector,
    val iconColor: Color
)

// Data sampel untuk notifikasi
val sampleNotifications = listOf(
    NotificationData(
        id = 1,
        title = "Peringatan Cuaca Laut",
        message = "Prediksi curah hujan tinggi dan berangin, sebaiknya jangan ke laut hari ini.",
        timestamp = "Baru saja",
        icon = Icons.Default.Warning,
        iconColor = Color(0xFFD32F2F) // Merah untuk peringatan
    ),
    NotificationData(
        id = 2,
        title = "Saran Musim Tanam",
        message = "Memasuki 'Keuneunong Ròt' (Keuneunong 3). Waktu yang baik untuk mulai mengolah sawah dan menanam padi.",
        timestamp = "2 jam lalu",
        icon = Icons.Default.Info,
        iconColor = Color(0xFF1976D2) // Biru untuk info
    ),
    NotificationData(
        id = 3,
        title = "Musim Kemarau",
        message = "Waspada musim 'Keuneunong Boh Kayèe' (Keuneunong 9). Perhatikan irigasi untuk tanaman palawija Anda.",
        timestamp = "1 hari lalu",
        icon = Icons.Default.WbSunny,
        iconColor = Color(0xFFFFA000) // Oranye/Amber untuk cuaca
    ),
    NotificationData(
        id = 4,
        title = "Info Perawatan",
        message = "Pembaruan rutin: Jadwal pemupukan tanaman Anda telah diperbarui di kalender.",
        timestamp = "3 hari lalu",
        icon = Icons.Default.Info,
        iconColor = Color(0xFF1976D2)
    )
)

@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50) // Latar belakang dari file asli
    ) {
        // Judul Halaman
        Text(
            text = "Notifikasi",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900, // Warna dari file asli
            modifier = Modifier.padding(16.dp)
        )

        // Daftar Notifikasi
        if (sampleNotifications.isEmpty()) {
            // Tampilan jika tidak ada notifikasi
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada notifikasi baru.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray500 // Warna dari file asli
                )
            }
        } else {
            // Tampilan daftar notifikasi
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleNotifications) { notification ->
                    NotificationItem(notification = notification)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Warna dari file asli
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = notification.icon,
                contentDescription = "Ikon Notifikasi",
                tint = notification.iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Gray900 // Warna dari file asli
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700 // Warna dari file asli
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray500 // Warna dari file asli
                )
            }
        }
    }
}