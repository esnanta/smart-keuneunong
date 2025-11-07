package com.smart.keuneunong.ui.users

import com.smart.keuneunong.domain.User

data class UsersUiState(
    val list: List<User> = listOf(),
    val offline: Boolean = false
)