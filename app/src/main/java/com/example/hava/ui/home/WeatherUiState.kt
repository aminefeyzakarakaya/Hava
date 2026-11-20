package com.example.hava.ui.home

import androidx.annotation.StringRes
import com.example.hava.data.remote.WeatherResponse

sealed interface WeatherUiState {
    data object Idle : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val weather: WeatherResponse) : WeatherUiState
    data class Error(@StringRes val messageRes: Int) : WeatherUiState
}
