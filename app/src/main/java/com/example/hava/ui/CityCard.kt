package com.example.hava.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hava.ui.theme.HavaTheme

@Composable
fun CityCard(
    cityName: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    isNight: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isNight && isSelected -> Color(0xFF2A527C)
                isNight -> Color(0xFF193655)
                isSelected -> Color(0xFFD8EEFF)
                else -> Color(0xFFFFFEFC)
            },
            contentColor = if (isNight) Color(0xFFF8FAFF) else Color(0xFF1D3658)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.titleMedium
            )
            if (isSelected) {
                Text(
                    text = "Seçili",
                    color = if (isNight) Color(0xFFFFD46F) else Color(0xFF438DE0),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CityCardPreview() {
    HavaTheme {
        CityCard(
            cityName = "Ankara",
            isSelected = true,
            onClick = {},
            isNight = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}
