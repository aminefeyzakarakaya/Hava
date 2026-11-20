package com.example.hava.ui.components

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
import com.example.hava.ui.theme.CityCardColors
import com.example.hava.ui.theme.HavaBlue
import com.example.hava.ui.theme.HavaNavy
import com.example.hava.ui.theme.HavaNightCard
import com.example.hava.ui.theme.HavaSky
import com.example.hava.ui.theme.HavaSurfaceLight
import com.example.hava.ui.theme.HavaTextLight
import androidx.compose.ui.res.stringResource
import com.example.hava.R

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
                isNight && isSelected -> CityCardColors.SelectedNight
                isNight -> HavaNightCard
                isSelected -> HavaSky
                else -> HavaSurfaceLight
            },
            contentColor = if (isNight) HavaTextLight else HavaNavy
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
                    text = stringResource(R.string.city_selected),
                    color = if (isNight) CityCardColors.SelectedLabelNight else HavaBlue,
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
