package com.smart.keuneunong.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow

@Composable
fun QuickStatCard(icon: String, title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .shadow(2.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                color = Color(0xFF64748B),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = value,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
