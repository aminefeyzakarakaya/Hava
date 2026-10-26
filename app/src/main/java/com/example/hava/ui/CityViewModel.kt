
package com.example.hava.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hava.data.CityApiClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

import com.example.hava.data.ProvinceApiClient
import com.example.hava.data.Province
import com.example.hava.data.CurrentWeather
import com.example.hava.data.WeatherApiClient
import com.example.hava.data.WeatherResponse

class CityViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Arama kutusu
    var aramaMetni by mutableStateOf(
        savedStateHandle.get<String>("aramaMetni") ?: ""
    )
        private set

    fun aramaMetniniGuncelle(yeniMetin: String) {
        aramaMetni = yeniMetin
        savedStateHandle["aramaMetni"] = yeniMetin
    }

    // Seçilen şehir
    var secilenSehir by mutableStateOf(
        savedStateHandle.get<String>("secilenSehir")
            ?.takeIf { it.isNotBlank() }
            ?: ""
    )
        private set

    fun sehriSec(sehirAdi: String) {
        secilenSehir = sehirAdi
        savedStateHandle["secilenSehir"] = sehirAdi
    }

    // API durumu
    var aramaDurumu by mutableStateOf<CitySearchUiState>(
        CitySearchUiState.Idle
    )
        private set

    private var sonArananSehir = "Bursa"

    fun internettenSehirAra(sehirAdi: String) {
        val temizAd = sehirAdi.trim()

        if (temizAd.length < 2) {
            aramaDurumu = CitySearchUiState.Error(
                "En az iki harf yazmalısın."
            )
            return
        }

        sonArananSehir = temizAd

        viewModelScope.launch {
            aramaDurumu = CitySearchUiState.Loading

            try {
                val yanit = CityApiClient.api.searchCities(temizAd)
                val sehirler = yanit.results.orEmpty()

                aramaDurumu = if (sehirler.isEmpty()) {
                    CitySearchUiState.Empty
                } else {
                    CitySearchUiState.Success(sehirler)
                }

                Log.d(
                    "CityApi",
                    "Gelen şehirler: ${sehirler.map { it.name }}"
                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                aramaDurumu = CitySearchUiState.Error(
                    "Bağlantı hatası oluştu. Tekrar deneyebilirsin."
                )
                Log.e("CityApi", "Şehir arama hatası", e)
            }
        }
    }

    fun tekrarDene() {
        internettenSehirAra(sonArananSehir)
    }

    var iller by mutableStateOf<List<Province>>(emptyList())
        private set

    var illerYukleniyor by mutableStateOf(false)
        private set

    var illerHatasi by mutableStateOf<String?>(null)
        private set

    init {
        illeriYukle()
    }

    fun illeriYukle() {
        viewModelScope.launch {
            illerYukleniyor = true
            illerHatasi = null

            try {
                val yuklenenIller = ProvinceApiClient.api
                    .getProvinces()
                    .data
                    .sortedBy { it.name }
                iller = yuklenenIller

                yuklenenIller
                    .firstOrNull { it.name.equals(secilenSehir, ignoreCase = true) }
                    ?.let(::ilinHavasiniGetir)

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                illerHatasi = "İller yüklenemedi."
                Log.e("ProvinceApi", "İl listesi hatası", e)

            } finally {
                illerYukleniyor = false
            }
        }
    }

    var havaDurumu by mutableStateOf<CurrentWeather?>(null)
        private set
    var tahmin by mutableStateOf<WeatherResponse?>(null)
        private set

    var havaYukleniyor by mutableStateOf(false)
        private set

    var havaHatasi by mutableStateOf<String?>(null)
        private set

    fun ilinHavasiniGetir(il: Province) {
        val konum = il.coordinates

        if (konum == null) {
            havaHatasi = "Bu ilin koordinatları bulunamadı."
            havaDurumu = null
            tahmin = null
            return
        }

        havayiKoordinatlardanGetir(
            latitude = konum.latitude,
            longitude = konum.longitude,
            sehirAdi = il.name
        )
    }

    fun konumdanHavasiniGetir(
        latitude: Double,
        longitude: Double,
        sehirAdi: String
    ) {
        val gosterilecekSehir = sehirAdi.ifBlank { "Konumum" }
        sehriSec(gosterilecekSehir)
        havayiKoordinatlardanGetir(latitude, longitude, gosterilecekSehir)
    }

    private fun havayiKoordinatlardanGetir(
        latitude: Double,
        longitude: Double,
        sehirAdi: String
    ) {
        viewModelScope.launch {
            havaYukleniyor = true
            havaHatasi = null
            havaDurumu = null
            tahmin = null

            try {
                val yanit = WeatherApiClient.api.getWeather(
                    latitude = latitude,
                    longitude = longitude
                )

                if (secilenSehir == sehirAdi) {
                    havaDurumu = yanit.current
                    tahmin = yanit
                }

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                if (secilenSehir == sehirAdi) {
                    havaHatasi = "Hava durumu alınamadı."
                }
                Log.e("WeatherApi", "Hava durumu hatası", e)

            } finally {
                if (secilenSehir == sehirAdi) {
                    havaYukleniyor = false
                }
            }
        }
    }

}
