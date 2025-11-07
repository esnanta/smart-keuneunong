package com.smart.keuneunong.ui.details

import com.smart.keuneunong.domain.Details
import com.smart.keuneunong.util.formatDate

data class DetailsUiState(
    val detail: Details = Details(),
    val offline: Boolean = false
) {
    val formattedUserSince = formatDate(detail.userSince)
}