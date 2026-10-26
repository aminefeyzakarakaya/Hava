package com.example.hava.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.content.ContextCompat
import com.example.hava.data.DeviceLocationClient
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SehirListesi(modifier: Modifier = Modifier) {
    val cityViewModel: CityViewModel = viewModel()
    val context = LocalContext.current
    val locationClient = remember(context) { DeviceLocationClient(context) }
    val coroutineScope = rememberCoroutineScope()
    var sehirPaneliAcik by rememberSaveable { mutableStateOf(false) }
    var konumYukleniyor by rememberSaveable { mutableStateOf(false) }
    var konumHatasi by rememberSaveable { mutableStateOf<String?>(null) }
    var konumIzinIstendi by rememberSaveable { mutableStateOf(false) }

    fun konumuAl() {
        coroutineScope.launch {
            konumYukleniyor = true
            konumHatasi = null

            try {
                val location = locationClient.getCurrentLocation()
                if (location == null) {
                    konumHatasi = "Konum bulunamadı. Şehir seçerek devam edebilirsin."
                } else {
                    val cityName = locationClient.getCityName(
                        latitude = location.latitude,
                        longitude = location.longitude
                    ) ?: "Konumum"

                    cityViewModel.konumdanHavasiniGetir(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        sehirAdi = cityName
                    )
                }
            } catch (_: Exception) {
                konumHatasi = "Konum alınamadı. Şehir seçerek devam edebilirsin."
            } finally {
                konumYukleniyor = false
            }
        }
    }

    val konumIzinBaslatici = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { izinVerildi ->
        if (izinVerildi) {
            konumuAl()
        } else {
            konumHatasi = "Konum izni verilmedi. Şehir seçerek devam edebilirsin."
        }
    }

    LaunchedEffect(Unit) {
        val izinVar = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (izinVar) {
            konumuAl()
        } else if (!konumIzinIstendi) {
            konumIzinIstendi = true
            konumIzinBaslatici.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    val hava = cityViewModel.havaDurumu
    val gece = hava?.isDay == 0
    val metinRengi = if (gece) Color(0xFFF8FAFF) else Color(0xFF1D3658)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "weather_hero") {
            WeatherHero(
                sehirAdi = cityViewModel.secilenSehir,
                hava = hava,
                yukleniyor = konumYukleniyor || cityViewModel.havaYukleniyor,
                hata = konumHatasi ?: cityViewModel.havaHatasi,
                onSehirSec = { sehirPaneliAcik = true }
            )
        }

        if (hava != null) {
            cityViewModel.tahmin?.let { tahmin ->
                item(key = "hourly_forecast") {
                    SaatlikTahmin(yanit = tahmin, gece = gece)
                }
            }

            item(key = "weather_music") {
                WeatherMusicCard(
                    suggestion = musicSuggestionForWeather(
                        weatherCode = hava.weatherCode,
                        isDay = hava.isDay == 1
                    ),
                    isNight = gece
                )
            }

            item(key = "today_details") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Bugünün detayları",
                        modifier = Modifier.fillMaxWidth(),
                        color = metinRengi,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HavaDetayKarti(
                            baslik = "Nem",
                            deger = "%${hava.humidity}",
                            gece = gece,
                            weatherCode = hava.weatherCode,
                            modifier = Modifier.weight(1f)
                        )
                        HavaDetayKarti(
                            baslik = "Rüzgâr",
                            deger = "${hava.windSpeed.roundToInt()} km/sa",
                            gece = gece,
                            weatherCode = hava.weatherCode,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HavaDetayKarti(
                            baslik = "Hissedilen",
                            deger = "${hava.feelsLike.roundToInt()}°",
                            gece = gece,
                            weatherCode = hava.weatherCode,
                            modifier = Modifier.weight(1f)
                        )
                        HavaDetayKarti(
                            baslik = "Koşullar",
                            deger = havaAciklamasi(hava.weatherCode),
                            gece = gece,
                            weatherCode = hava.weatherCode,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            cityViewModel.tahmin?.let { tahmin ->
                item(key = "weekly_forecast") {
                    HaftalikTahmin(yanit = tahmin, gece = gece)
                }
            }
        }
    }

    if (sehirPaneliAcik) {
        ModalBottomSheet(onDismissRequest = { sehirPaneliAcik = false }) {
            val aramaMetni = cityViewModel.aramaMetni.trim()
            val filtreliIller = remember(cityViewModel.iller, aramaMetni) {
                cityViewModel.iller.filter { il ->
                    il.name.contains(aramaMetni, ignoreCase = true)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.82f)
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Şehir seç",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Hava durumunu görmek istediğin ili seç.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = cityViewModel.aramaMetni,
                    onValueChange = cityViewModel::aramaMetniniGuncelle,
                    label = { Text("İl ara") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                TextButton(
                    onClick = {
                        val izinVar = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        if (izinVar) {
                            konumuAl()
                        } else {
                            konumIzinIstendi = true
                            konumIzinBaslatici.launch(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        }
                        sehirPaneliAcik = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("⌖ Konumumu kullan")
                }

                if (cityViewModel.illerYukleniyor) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                cityViewModel.illerHatasi?.let { hata ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(hata, color = MaterialTheme.colorScheme.error)
                        TextButton(onClick = cityViewModel::illeriYukle) {
                            Text("Tekrar dene")
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    if (!cityViewModel.illerYukleniyor &&
                        cityViewModel.illerHatasi == null &&
                        filtreliIller.isEmpty()
                    ) {
                        item(key = "no_results") {
                            Text(
                                text = "Sonuç bulunamadı",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    items(filtreliIller, key = { it.name }) { il ->
                        CityCard(
                            cityName = il.name,
                            isSelected = il.name == cityViewModel.secilenSehir,
                            isNight = gece,
                            onClick = {
                                konumHatasi = null
                                cityViewModel.sehriSec(il.name)
                                cityViewModel.ilinHavasiniGetir(il)
                                cityViewModel.aramaMetniniGuncelle("")
                                sehirPaneliAcik = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HavaDetayKarti(
    baslik: String,
    deger: String,
    gece: Boolean,
    weatherCode: Int,
    modifier: Modifier = Modifier
) {
    val yaziRengi = if (gece) Color(0xFFF8FAFF) else Color(0xFF1D3658)
    val kartRengi = when {
        gece -> Color(0xFF193655)
        weatherCode in 51..67 || weatherCode in 80..82 || weatherCode in 95..99 -> Color(0xFFD2E4F0)
        weatherCode in 71..77 || weatherCode == 85 || weatherCode == 86 -> Color(0xFFE8F4FC)
        weatherCode == 3 || weatherCode in 45..48 -> Color(0xFFE1E8F1)
        weatherCode == 2 -> Color(0xFFDCEAF8)
        else -> Color(0xFFE6F1FA)
    }
    Surface(
        modifier = modifier.heightIn(min = 106.dp),
        shape = RoundedCornerShape(20.dp),
        color = kartRengi
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            WeatherDetailIcon(label = baslik, night = gece)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(baslik, color = yaziRengi.copy(alpha = 0.82f),
                    style = MaterialTheme.typography.bodyMedium)
                Text(deger, color = yaziRengi, fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold, lineHeight = 22.sp)
            }
        }
    }
}
