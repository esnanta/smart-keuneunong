package com.smart.keuneunong.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smart.keuneunong.domain.UserDetails

@Entity
data class DetailsEntity constructor(
    @PrimaryKey
    val user: String,
    val avatar: String,
    val name: String,
    val userSince: String,
    val location: String
)

fun DetailsEntity.asDomainModel(): UserDetails {
    return UserDetails(
        user = user,
        avatar = avatar,
        name = name,
        userSince = userSince,
        location = location
    )
}