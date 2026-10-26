package com.example.hava.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.data.WeatherResponse
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

private fun kartRenkleri(gece: Boolean, kod: Int): Pair<Color, Color> {
    val background = when {
        gece -> Color(0xFF193655)
        kod in 51..67 || kod in 80..82 || kod in 95..99 -> Color(0xFFD2E4F0)
        kod in 71..77 || kod == 85 || kod == 86 -> Color(0xFFE8F4FC)
        kod == 3 || kod in 45..48 -> Color(0xFFE1E8F1)
        kod == 2 -> Color(0xFFDCEAF8)
        else -> Color(0xFFE6F1FA)
    }
    val foreground = if (gece) Color(0xFFF8FAFF) else Color(0xFF1D3658)
    return background to foreground
}

@Composable
fun SaatlikTahmin(yanit: WeatherResponse, gece: Boolean) {
    val saatlik = yanit.hourly
    val adet = minOf(saatlik.time.size, saatlik.temperatures.size,
        saatlik.weatherCodes.size, saatlik.dayFlags.size)
    val simdikiSaat = yanit.current.time.take(13) // YYYY-MM-DDTHH
    val ilkIndex = saatlik.time.indexOfFirst { it.take(13) >= simdikiSaat }
        .let { if (it < 0) 0 else it }
    val indexler = (ilkIndex until minOf(ilkIndex + 12, adet)).toList()
    if (indexler.isEmpty()) return

    val (kart, yazi) = kartRenkleri(gece, yanit.current.weatherCode)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Saatlik tahmin", color = if (gece) Color.White else yazi,
            style = MaterialTheme.typography.titleMedium)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(end = 18.dp)
        ) {
            items(indexler) { index ->
                Surface(
                    shape = RoundedCornerShape(20.dp), color = kart,
                    modifier = Modifier.width(84.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = if (index == ilkIndex) "Şimdi"
                            else saatlik.time[index].takeLast(5),
                            color = yazi.copy(alpha = 0.85f), fontSize = 12.sp
                        )
                        KucukHavaIkonu(
                            kod = saatlik.weatherCodes[index],
                            gece = saatlik.dayFlags[index] == 0
                        )
                        Text("${saatlik.temperatures[index].roundToInt()}°",
                            color = yazi, fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun HaftalikTahmin(yanit: WeatherResponse, gece: Boolean) {
    val gunluk = yanit.daily
    val adet = minOf(gunluk.time.size, gunluk.weatherCodes.size,
        gunluk.maxTemperatures.size, gunluk.minTemperatures.size, 7)
    if (adet == 0) return

    val (kart, yazi) = kartRenkleri(gece, yanit.current.weatherCode)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("7 günlük tahmin", color = if (gece) Color.White else yazi,
            style = MaterialTheme.typography.titleMedium)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(end = 18.dp)
        ) {
            items((0 until adet).toList()) { index ->
                Surface(
                    shape = RoundedCornerShape(20.dp), color = kart,
                    modifier = Modifier.width(118.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Text(
                            text = if (index == 0) "Bugün" else gunAdi(gunluk.time[index]),
                            color = yazi, fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        KucukHavaIkonu(kod = gunluk.weatherCodes[index], gece = false)
                        Text(
                            text = havaAciklamasi(gunluk.weatherCodes[index]),
                            color = yazi.copy(alpha = 0.85f), fontSize = 11.sp,
                            maxLines = 1, textAlign = TextAlign.Center
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("${gunluk.maxTemperatures[index].roundToInt()}°",
                                color = yazi, fontWeight = FontWeight.SemiBold)
                            Text("${gunluk.minTemperatures[index].roundToInt()}°",
                                color = yazi.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}

private fun gunAdi(tarih: String): String = try {
    val tarihNesnesi = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(tarih)
    if (tarihNesnesi == null) tarih
    else SimpleDateFormat("EEE", Locale.forLanguageTag("tr-TR")).format(tarihNesnesi)
        .replaceFirstChar { it.uppercaseChar() }
} catch (_: Exception) { tarih }

// Ana ekrandaki WeatherHero ile aynı dil: sade, dolgulu, yuvarlak çizimli ikonlar.
@Composable
private fun KucukHavaIkonu(kod: Int, gece: Boolean) {
    Canvas(modifier = Modifier.size(46.dp)) {
        val w = size.width
        val h = size.height
        val sari = Color(0xFFF2B950)
        val bulut = if (gece) Color(0xFFE3EBF7) else Color(0xFFCBD7E2)
        val yagmur = kod in 51..67 || kod in 80..82 || kod in 95..99
        val kar = kod in 71..77 || kod == 85 || kod == 86
        val sis = kod == 45 || kod == 48
        val bulutlu = kod >= 2 || yagmur || kar || sis

        if (gece) {
            val dis = Path().apply {
                addOval(Rect(w * .30f, h * .08f, w * .72f, h * .55f))
            }
            val ic = Path().apply {
                addOval(Rect(w * .43f, h * .02f, w * .83f, h * .45f))
            }
            drawPath(Path.combine(PathOperation.Difference, dis, ic), sari)
        } else if (kod <= 2) {
            drawCircle(sari, radius = w * .20f,
                center = Offset(w * .59f, h * .38f))
        }
        if (bulutlu) {
            drawRoundRect(bulut, topLeft = Offset(w * .13f, h * .52f),
                size = Size(w * .74f, h * .24f),
                cornerRadius = CornerRadius(w * .12f))
            drawCircle(bulut, radius = w * .19f,
                center = Offset(w * .42f, h * .49f))
            drawCircle(bulut, radius = w * .14f,
                center = Offset(w * .65f, h * .54f))
        }
        if (yagmur) repeat(3) { i ->
            val x = w * (.31f + i * .19f)
            drawLine(Color(0xFF6EABDD), Offset(x, h * .82f),
                Offset(x - w * .035f, h * .93f),
                strokeWidth = w * .045f, cap = StrokeCap.Round)
        }
        if (kar) repeat(3) { i ->
            val center = Offset(w * (.30f + i * .19f), h * .88f)
            val arm = w * .06f
            drawLine(Color(0xFF73B9E8), Offset(center.x - arm, center.y),
                Offset(center.x + arm, center.y), w * .04f, StrokeCap.Round)
            drawLine(Color(0xFF73B9E8), Offset(center.x, center.y - arm),
                Offset(center.x, center.y + arm), w * .04f, StrokeCap.Round)
        }
        if (sis) repeat(2) { i ->
            val y = h * (.82f + i * .09f)
            drawLine(Color(0xFF9BBBD0), Offset(w * .28f, y), Offset(w * .72f, y),
                w * .045f, StrokeCap.Round)
        }
    }
}
