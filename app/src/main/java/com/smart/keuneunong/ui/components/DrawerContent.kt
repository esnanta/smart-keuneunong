package com.smart.keuneunong.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerContent(
    onLocationClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // App Title
        Text(
            text = "Smart Keuneunong",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Kalender Tradisional Aceh",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(24.dp))
        androidx.compose.material3.HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Menu Items
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            label = { Text("Lokasi Pengguna") },
            selected = false,
            onClick = onLocationClick
        )
        Spacer(modifier = Modifier.height(8.dp))
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            label = { Text("Tentang Aplikasi") },
            selected = false,
            onClick = onAboutClick
        )

        // Developer Team Section
        Spacer(modifier = Modifier.height(24.dp))
        androidx.compose.material3.HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bintang Kala",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TeamMemberItem(
            name = "Nantha Seutia",
            role = "Programmer"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TeamMemberItem(
            name = "Syahrul Hamdi",
            role = "Programmer"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TeamMemberItem(
            name = "Rahmatsyah",
            role = "Pegiat Budaya Aceh"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TeamMemberItem(
            name = "Nyakman Lamjame",
            role = "Pegiat Budaya Aceh"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TeamMemberItem(
            name = "Reny Fhareni",
            role = "Pegiat Budaya Aceh"
        )

        Spacer(modifier = Modifier.weight(1f))

        // Footer
        Text(
            text = "Smart Keuneunong © 2025",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TeamMemberItem(
    name: String,
    role: String
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with initials
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color(0xFF1976D2),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getInitials(name),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E1E1E)
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF64748B),
                fontSize = 13.sp
            )
        }
    }
}

/**
 * Get initials from a name (e.g., "Nantha Seutia" -> "NS")
 */
private fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.size >= 2 -> "${parts[0].first().uppercaseChar()}${parts[1].first().uppercaseChar()}"
        parts.size == 1 && parts[0].length >= 2 -> parts[0].take(2).uppercase()
        parts.size == 1 -> parts[0].first().uppercase()
        else -> "?"
    }
}
