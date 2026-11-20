package com.example.hava.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hava.domain.WeatherCondition
import com.example.hava.ui.components.DailyForecast
import com.example.hava.ui.components.HourlyForecast
import com.example.hava.ui.components.WeatherHero
import com.example.hava.ui.components.WeatherMusicCard
import com.example.hava.ui.components.musicSuggestionForWeather
import androidx.compose.ui.res.stringResource

private fun hasLocationPermission(context: Context): Boolean =
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val forecast = (weatherState as? WeatherUiState.Success)?.weather
    val current = forecast?.current
    val isWeatherLoading = weatherState is WeatherUiState.Loading
    val weatherErrorRes = (weatherState as? WeatherUiState.Error)?.messageRes
    val isNight = current?.isDay == 0

    val context = LocalContext.current
    var isCityPickerOpen by rememberSaveable { mutableStateOf(false) }
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.fetchLocation() else viewModel.onLocationPermissionDenied()
    }

    // Panelden "Konumumu kullan" basılınca: izin varsa konumu al, yoksa önce izin iste
    fun useMyLocation() {
        if (hasLocationPermission(context)) {
            viewModel.fetchLocation()
        } else {
            permissionRequested = true
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    LaunchedEffect(Unit) {
        if (hasLocationPermission(context)) {
            viewModel.fetchLocationOnStart()
        } else if (!permissionRequested) {
            permissionRequested = true
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "weather_hero") {
            WeatherHero(
                cityName = viewModel.selectedCity,
                weather = current,
                isLoading = viewModel.isLocating || isWeatherLoading,
                errorMessage = (viewModel.locationError ?: weatherErrorRes)?.let { stringResource(it) },
                onCityClick = { isCityPickerOpen = true }
            )
        }

        if (current != null && forecast != null) {
            item(key = "hourly_forecast") {
                HourlyForecast(weather = forecast, isNight = isNight)
            }

            item(key = "weather_music") {
                WeatherMusicCard(
                    song = musicSuggestionForWeather(
                        condition = WeatherCondition.fromCode(current.weatherCode),
                        isDay = current.isDay == 1
                    ),
                    isNight = isNight
                )
            }

            item(key = "today_details") {
                TodayDetails(weather = current, isNight = isNight)
            }

            item(key = "daily_forecast") {
                DailyForecast(weather = forecast, isNight = isNight)
            }
        }
    }

    if (isCityPickerOpen) {
        CityPickerSheet(
            provinces = viewModel.provinces,
            selectedCity = viewModel.selectedCity,
            searchQuery = viewModel.searchQuery,
            isNight = isNight,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onUseMyLocation = {
                isCityPickerOpen = false
                useMyLocation()
            },
            onProvinceSelected = { province ->
                viewModel.selectProvince(province)
                isCityPickerOpen = false
            },
            onDismiss = { isCityPickerOpen = false }
        )
    }
}
