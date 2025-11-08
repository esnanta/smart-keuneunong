package com.smart.keuneunong.data.model

data class KeuneunongPhase(
    val id: Int,
    val name: String,
    val icon: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val activities: List<String>
)
