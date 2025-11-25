package com.smart.keuneunong.domain.repository

interface LocationRepository {
    fun getCityName(latitude: Double, longitude: Double): String
}

