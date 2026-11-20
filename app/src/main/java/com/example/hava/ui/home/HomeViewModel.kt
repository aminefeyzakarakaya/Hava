package com.example.hava.ui.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hava.data.Province
import com.example.hava.data.Provinces
import com.example.hava.data.location.DeviceLocationClient
import com.example.hava.data.location.LocationClient
import com.example.hava.data.remote.WeatherApiClient
import com.example.hava.data.repository.DefaultWeatherRepository
import com.example.hava.data.repository.WeatherRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.hava.R

class HomeViewModel(
    private val repository: WeatherRepository,
    private val locationClient: LocationClient,
    private val savedStateHandle: SavedStateHandle,
    private val fallbackCityName: String
) : ViewModel() {

    // Arama kutusu
    var searchQuery by mutableStateOf(savedStateHandle.get<String>(KEY_SEARCH_QUERY) ?: "")
        private set

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        savedStateHandle[KEY_SEARCH_QUERY] = newQuery
    }

    // Seçilen şehir
    var selectedCity by mutableStateOf(
        savedStateHandle.get<String>(KEY_SELECTED_CITY)?.takeIf { it.isNotBlank() } ?: ""
    )
        private set

    private fun updateSelectedCity(cityName: String) {
        selectedCity = cityName
        savedStateHandle[KEY_SELECTED_CITY] = cityName
    }

    val provinces: List<Province> = Provinces.all

    // Hava durumu
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    // Konum durumu. ViewModel ekran dönse de yaşadığı için yükleme durumu takılı kalmaz.
    var isLocating by mutableStateOf(false)
        private set
    var locationError by mutableStateOf<Int?>(null)
        private set

    // Yeni istek gelince eskisi iptal edilir, böylece en son seçim her zaman kazanır
    private var weatherJob: Job? = null
    private var locationJob: Job? = null
    private var startupLocationRequested = false

    init {
        provinces.firstOrNull { it.name.equals(selectedCity, ignoreCase = true) }
            ?.let { loadWeather(it.latitude, it.longitude) }
    }

    fun selectProvince(province: Province) {
        locationJob?.cancel()
        isLocating = false
        locationError = null
        updateSelectedCity(province.name)
        onSearchQueryChange("")
        loadWeather(province.latitude, province.longitude)
    }

    /** Açılışta bir kez çalışır. Ekran dönünce tekrar konum çekip seçili şehri ezmez. */
    fun fetchLocationOnStart() {
        if (startupLocationRequested) return
        startupLocationRequested = true
        fetchLocation()
    }

    fun onLocationPermissionDenied() {
        locationError = R.string.error_location_denied
    }

    fun fetchLocation() {
        locationJob?.cancel()
        locationJob = viewModelScope.launch {
            isLocating = true
            locationError = null

            try {
                val location = locationClient.getCurrentLocation()
                if (location == null) {
                    locationError = R.string.error_location_not_found
                } else {
                    val cityName = locationClient
                        .getCityName(location.latitude, location.longitude)
                        ?.ifBlank { null }
                        ?: fallbackCityName
                    updateSelectedCity(cityName)
                    loadWeather(location.latitude, location.longitude)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Konum hatası", e)
                locationError = R.string.error_location_failed
            } finally {
                isLocating = false
            }
        }
    }

    private fun loadWeather(latitude: Double, longitude: Double) {
        weatherJob?.cancel()
        weatherJob = viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            _weatherState.value = try {
                WeatherUiState.Success(repository.getWeather(latitude, longitude))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Hava durumu hatası", e)
                WeatherUiState.Error(R.string.error_weather_failed)
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val KEY_SELECTED_CITY = "selected_city"

        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                HomeViewModel(
                    repository = DefaultWeatherRepository(WeatherApiClient.api),
                    locationClient = DeviceLocationClient(application),
                    savedStateHandle = createSavedStateHandle(),
                    fallbackCityName = application.getString(R.string.my_location)
                )
            }
        }
    }
}
