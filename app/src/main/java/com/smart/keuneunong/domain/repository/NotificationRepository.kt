package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.ui.notification.NotificationData

interface NotificationRepository {
    suspend fun getNotifications(): List<NotificationData>
}

