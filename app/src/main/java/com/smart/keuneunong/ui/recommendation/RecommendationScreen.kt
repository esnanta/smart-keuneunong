package com.smart.keuneunong.ui.recommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.keuneunong.ui.components.AppHeader
import com.smart.keuneunong.ui.theme.*
import com.smart.keuneunong.utils.DateUtils

@Composable
fun RecommendationScreen(
    paddingValues: PaddingValues = PaddingValues(),
    openDrawer: () -> Unit = {},
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is RecommendationUiState.Loading -> LoadingState(paddingValues)
        is RecommendationUiState.Error -> ErrorState((uiState as RecommendationUiState.Error).message, paddingValues)
        is RecommendationUiState.Success -> SuccessState(
            data = uiState as RecommendationUiState.Success,
            paddingValues = paddingValues,
            openDrawer = openDrawer
        )
    }
}

@Composable
private fun LoadingState(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: String, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun SuccessState(
    data: RecommendationUiState.Success,
    paddingValues: PaddingValues,
    openDrawer: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        item {
            AppHeader(
                title = "Smart Keuneunong",
                subtitle = DateUtils.getCurrentDateFormatted(),
                showGreeting = true,
                onMenuClick = openDrawer
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            CurrentKeuneunongCard(
                periodName = data.periodName,
                description = data.periodDescription
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp)
            ) {
                HorizontalDivider(
                    color = Gray100,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = "Rekomendasi Sektor",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Gray900,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )
            }
        }
        items(data.sectorRecommendations.size) { idx ->
            val sector = data.sectorRecommendations[idx]
            RecommendationCard(
                title = sector.title,
                icon = sector.icon,
                recommendations = sector.recommendations,
                notes = sector.notes
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun CurrentKeuneunongCard(periodName: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Saat Ini Memasuki:",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = periodName,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
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
            notes?.let {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = Gray100
                )
                RecommendationItem(text = it, isRecommended = false)
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