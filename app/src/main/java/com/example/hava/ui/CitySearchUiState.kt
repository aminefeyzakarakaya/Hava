
package com.example.hava.ui

import com.example.hava.data.ApiCity

sealed interface CitySearchUiState {

    data object Idle : CitySearchUiState

    data object Loading : CitySearchUiState

    data class Success(
        val sehirler: List<ApiCity>
    ) : CitySearchUiState

    data object Empty : CitySearchUiState

    data class Error(
        val mesaj: String
    ) : CitySearchUiState
}
