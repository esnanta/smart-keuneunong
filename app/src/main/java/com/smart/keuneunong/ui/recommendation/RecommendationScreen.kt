package com.smart.keuneunong.ui.recommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.keuneunong.ui.theme.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecommendationScreen(
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Gray900
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