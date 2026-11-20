package com.example.hava.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hava.data.Province
import com.example.hava.ui.components.CityCard
import androidx.compose.ui.res.stringResource
import com.example.hava.R

/** Şehir seçme paneli. ViewModel bilmez, sadece verilen veriyi gösterir ve olayları dışarı bildirir. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityPickerSheet(
    provinces: List<Province>,
    selectedCity: String,
    searchQuery: String,
    isNight: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onUseMyLocation: () -> Unit,
    onProvinceSelected: (Province) -> Unit,
    onDismiss: () -> Unit
) {
    val filteredProvinces = remember(provinces, searchQuery) {
        val query = searchQuery.trim()
        provinces.filter { it.name.contains(query, ignoreCase = true) }
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.82f)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.city_picker_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.city_picker_subtitle),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text(stringResource(R.string.city_search_label)) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(
                onClick = onUseMyLocation,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.use_my_location))
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                if (filteredProvinces.isEmpty()) {
                    item(key = "no_results") {
                        Text(
                            text = stringResource(R.string.no_results),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                items(filteredProvinces, key = { it.name }) { province ->
                    CityCard(
                        cityName = province.name,
                        isSelected = province.name == selectedCity,
                        isNight = isNight,
                        onClick = { onProvinceSelected(province) }
                    )
                }
            }
        }
    }
}
