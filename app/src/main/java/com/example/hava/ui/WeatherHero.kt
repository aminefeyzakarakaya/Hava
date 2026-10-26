package com.example.hava.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.data.CurrentWeather
import kotlin.math.roundToInt

@Composable
fun WeatherHero(
    sehirAdi: String,
    hava: CurrentWeather?,
    yukleniyor: Boolean,
    hata: String?,
    onSehirSec: () -> Unit
) {
    val gece = hava?.isDay == 0
    val yaziRengi = if (gece) Color(0xFFF7F8FF) else Color(0xFF17365A)
    val chipRengi = if (gece) Color(0xFF193655) else Color(0xFFD8EAF9)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        TextButton(
            onClick = onSehirSec,
            colors = ButtonDefaults.textButtonColors(
                contentColor = yaziRengi,
                containerColor = chipRengi
            )
        ) {
            Text(
                text = when {
                    sehirAdi.isNotBlank() -> "⌖ $sehirAdi  ⌄"
                    yukleniyor -> "⌖ Konum aranıyor…"
                    else -> "⌖ Şehir seç  ⌄"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        when {
            yukleniyor -> {
                Column(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Text("Hava durumu yükleniyor…", color = yaziRengi)
                }
            }

            hava == null -> {
                Column(
                    modifier = Modifier.padding(top = 12.dp, bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FlatWeatherIcon(kod = 2, gece = false, modifier = Modifier.size(92.dp))
                    Text(
                        text = hata ?: "Şehrini seç, hava durumunu birlikte görelim.",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = yaziRengi,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        Text(
                            text = "${hava.temperature.roundToInt()}°",
                            color = yaziRengi,
                            fontSize = 70.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 74.sp,
                            letterSpacing = (-2).sp
                        )
                        Text(
                            text = havaAciklamasi(hava.weatherCode),
                            color = yaziRengi,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Hissedilen ${hava.feelsLike.roundToInt()}°",
                            color = yaziRengi.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    FlatWeatherIcon(
                        kod = hava.weatherCode,
                        gece = gece,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(118.dp)
                    )
                }
            }
        }
    }
}

fun havaAciklamasi(kod: Int): String = when (kod) {
    0 -> "Açık"
    1 -> "Az bulutlu"
    2 -> "Parçalı bulutlu"
    3 -> "Bulutlu"
    45, 48 -> "Sisli"
    in 51..57 -> "Çisenti"
    in 61..67, in 80..82 -> "Yağmurlu"
    in 71..77, 85, 86 -> "Karlı"
    in 95..99 -> "Gök gürültülü"
    else -> "Hava durumu"
}

@Composable
private fun FlatWeatherIcon(kod: Int, gece: Boolean, modifier: Modifier = Modifier) {
    val yagmur = kod in 51..67 || kod in 80..82 || kod in 95..99
    val kar = kod in 71..77 || kod == 85 || kod == 86
    val sis = kod == 45 || kod == 48
    val bulutGerekli = kod >= 2 || yagmur || kar || sis

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val sari = Color(0xFFFFCF62)
        val bulut = if (gece) Color(0xFFE1ECFC) else Color(0xFFF8FCFF)

        if (gece) {
            val outside = Path().apply { addOval(Rect(w * .22f, h * .08f, w * .78f, h * .64f)) }
            val inside = Path().apply { addOval(Rect(w * .40f, h * .02f, w * .91f, h * .57f)) }
            drawPath(Path.combine(PathOperation.Difference, outside, inside), sari)
        } else if (kod <= 2) {
            val center = Offset(w * .52f, h * .35f)
            drawCircle(sari, w * .17f, center)
            repeat(8) { index ->
                val angle = index * Math.PI / 4.0
                val dx = kotlin.math.cos(angle).toFloat()
                val dy = kotlin.math.sin(angle).toFloat()
                drawLine(
                    color = sari,
                    start = Offset(center.x + dx * w * .21f, center.y + dy * w * .21f),
                    end = Offset(center.x + dx * w * .27f, center.y + dy * w * .27f),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        if (bulutGerekli) {
            drawRoundRect(
                color = bulut,
                topLeft = Offset(w * .10f, h * .49f),
                size = Size(w * .80f, h * .28f),
                cornerRadius = CornerRadius(w * .14f)
            )
            drawCircle(bulut, w * .21f, Offset(w * .39f, h * .49f))
            drawCircle(bulut, w * .16f, Offset(w * .67f, h * .53f))
        }

        when {
            yagmur -> repeat(4) { index ->
                val x = w * (.25f + index * .17f)
                val rainColor = if (kod in 51..57) Color(0xFF9BD9FF) else Color(0xFF58B6ED)
                drawLine(
                    color = rainColor,
                    start = Offset(x, h * .82f),
                    end = Offset(x - w * .04f, h * .94f),
                    strokeWidth = 3.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            kar -> repeat(3) { index ->
                val center = Offset(w * (.31f + index * .19f), h * .87f)
                val arm = w * .045f
                drawLine(Color(0xFFB6E9FF), Offset(center.x - arm, center.y), Offset(center.x + arm, center.y), 2.dp.toPx(), StrokeCap.Round)
                drawLine(Color(0xFFB6E9FF), Offset(center.x, center.y - arm), Offset(center.x, center.y + arm), 2.dp.toPx(), StrokeCap.Round)
                drawLine(Color(0xFFB6E9FF), Offset(center.x - arm * .7f, center.y - arm * .7f), Offset(center.x + arm * .7f, center.y + arm * .7f), 1.5.dp.toPx(), StrokeCap.Round)
            }
            sis -> repeat(2) { index ->
                val y = h * (.84f + index * .07f)
                drawLine(Color(0xFFB8D7E8), Offset(w * .28f, y), Offset(w * .76f, y), 2.dp.toPx(), StrokeCap.Round)
            }
        }

        if (kod in 95..99) {
            val bolt = Path().apply {
                moveTo(w * .55f, h * .78f)
                lineTo(w * .44f, h * .91f)
                lineTo(w * .53f, h * .91f)
                lineTo(w * .48f, h * .99f)
                lineTo(w * .65f, h * .85f)
                lineTo(w * .56f, h * .85f)
                close()
            }
            drawPath(bolt, Color(0xFFFFD064))
        }
    }
}
