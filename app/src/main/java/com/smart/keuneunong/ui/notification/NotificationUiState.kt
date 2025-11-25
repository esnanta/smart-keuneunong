package com.smart.keuneunong.ui.notification

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class NotificationData(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: String,
    val icon: ImageVector,
    val iconColor: Color
)

sealed class NotificationUiState {
    object Loading : NotificationUiState()
    data class Success(val notifications: List<NotificationData>) : NotificationUiState()
    data class Error(val message: String) : NotificationUiState()
}
