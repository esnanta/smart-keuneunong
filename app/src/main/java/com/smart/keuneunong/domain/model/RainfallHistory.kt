package com.smart.keuneunong.domain.model

/**
 * Domain Entity untuk data historis curah hujan
 */
data class RainfallHistory(
    val day: Int,
    val month: Int,
    val year: Int,
    val category: RainfallCategory,
    val amount: Double // dalam mm
)

/**
 * Kategori curah hujan dengan definisi warna
 */
enum class RainfallCategory(val colorHex: String, val label: String) {
    TINGGI("#2D68C4", "Tinggi"),           // True Blue
    SEDANG("#B5C7EB", "Sedang"),           // Misty Blue
    RENDAH("#FFDBBB", "Rendah"),           // Light Orange
    SANGAT_RENDAH("#FF4B33", "Sangat Rendah") // Red Orange
}

