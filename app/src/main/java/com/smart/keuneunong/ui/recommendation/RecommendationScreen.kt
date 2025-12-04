package com.smart.keuneunong.ui.recommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smart.keuneunong.ui.theme.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecommendationScreen(
    contentPadding: PaddingValues,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is RecommendationUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is RecommendationUiState.Error -> {
            val message = (uiState as RecommendationUiState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }
        }
        is RecommendationUiState.Success -> {
            val data = uiState as RecommendationUiState.Success

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Gray50)
                    .padding(contentPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                item {
                    Text(
                        text = "Rekomendasi Aktivitas",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Berdasarkan Keuneunong Saat Ini",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray500
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    CurrentKeuneunongCard(
                        periodName = data.periodName,
                        description = data.periodDescription
                    )
                }

                item {
                    Text(
                        text = "Rekomendasi Sektor",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                }

                items(data.sectorRecommendations.size) { idx ->
                    val sector = data.sectorRecommendations[idx]
                    RecommendationCard(
                        title = sector.title,
                        icon = sector.icon,
                        recommendations = sector.recommendations,
                        notes = sector.notes
                    )
                }
            }
        }
    }
}

@Composable
fun CurrentKeuneunongCard(periodName: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Saat Ini Memasuki:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = periodName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun RecommendationCard(
    title: String,
    icon: String,
    recommendations: List<String>,
    notes: String?
) {
    var showDetailDialog by remember { mutableStateOf(false) }

    // Popup Dialog
    if (showDetailDialog) {
        SectorDetailDialog(
            title = title,
            icon = icon,
            recommendations = recommendations,
            notes = notes,
            onDismiss = { showDetailDialog = false }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDetailDialog = true },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = icon, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Gray900,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Lihat detail $title",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                recommendations.forEach { recommendation ->
                    RecommendationItem(text = recommendation, isRecommended = true)
                }
            }


            if (notes != null) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Gray100)
                RecommendationItem(text = notes, isRecommended = false)
            }
        }
    }
}

@Composable
fun RecommendationItem(text: String, isRecommended: Boolean) {
    Row(verticalAlignment = Alignment.Top) {

        val icon = if (isRecommended) "✅" else "⚠️"
        val color = if (isRecommended) Gray700 else MaterialTheme.colorScheme.error

        Text(
            text = icon,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }
}


@Composable
fun SectorDetailDialog(
    title: String,
    icon: String,
    recommendations: List<String>,
    notes: String?,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Header with Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = icon,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Recommendations Section
                Text(
                    text = "Aktivitas yang Direkomendasikan:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray900
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    recommendations.forEach { recommendation ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "✅",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = recommendation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Gray700
                            )
                        }
                    }
                }

                // Notes Section
                if (notes != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Gray100)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Perhatian:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "⚠️",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = notes,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Close Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Tutup",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

