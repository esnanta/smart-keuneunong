package com.smart.keuneunong.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.Color
import com.smart.keuneunong.domain.repository.NotificationRepository
import com.smart.keuneunong.ui.notification.NotificationData
import kotlinx.coroutines.delay
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor() : NotificationRepository {
    override suspend fun getNotifications(): List<NotificationData> {
        delay(1000)
        return sampleNotifications
    }
}

private val sampleNotifications = listOf(
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

