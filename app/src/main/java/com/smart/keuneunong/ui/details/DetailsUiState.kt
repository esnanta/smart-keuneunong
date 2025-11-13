package com.smart.keuneunong.ui.details

import com.smart.keuneunong.domain.Details
import com.smart.keuneunong.utils.formatDate

data class DetailsUiState(
    val detail: Details = Details(),
    val offline: Boolean = false
) {
    val formattedUserSince = formatDate(detail.userSince)
}