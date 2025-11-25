package com.smart.keuneunong.ui.details

import com.smart.keuneunong.domain.UserDetails
import com.smart.keuneunong.utils.formatDate

data class DetailsUiState(
    val detail: UserDetails = UserDetails(),
    val offline: Boolean = false
) {
    val formattedUserSince = formatDate(detail.userSince)
}