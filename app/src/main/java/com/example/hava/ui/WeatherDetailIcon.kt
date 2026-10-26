package com.example.hava.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDetailIcon(label: String, night: Boolean, modifier: Modifier = Modifier) {
    val badge = when (label) {
        "Nem" -> Color(0xFFA9D5FF)
        "Rüzgâr" -> Color(0xFFA9DFED)
        "Hissedilen" -> Color(0xFFFFCDAA)
        else -> Color(0xFFFFDC80)
    }
    Box(modifier.size(40.dp).background(badge, CircleShape)
        .semantics { contentDescription = label }) {
        Canvas(Modifier.size(40.dp).padding(9.dp)) {
            val w = size.width
            val h = size.height
            val ink = Color(0xFF194773)
            val pen = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            when (label) {
                "Nem" -> {
                    val drop = Path().apply {
                        moveTo(w * .5f, h * .03f)
                        cubicTo(w * .42f, h * .2f, w * .12f, h * .48f, w * .15f, h * .7f)
                        cubicTo(w * .18f, h * 1.05f, w * .82f, h * 1.05f, w * .85f, h * .7f)
                        cubicTo(w * .88f, h * .48f, w * .58f, h * .2f, w * .5f, h * .03f)
                        close()
                    }
                    drawPath(drop, ink)
                }
                "Rüzgâr" -> {
                    val wind = Path().apply {
                        moveTo(0f, h * .30f)
                        lineTo(w * .65f, h * .30f)
                        cubicTo(w * .98f, h * .30f, w * .96f, 0f, w * .70f, h * .08f)
                        moveTo(0f, h * .51f)
                        lineTo(w * .84f, h * .51f)
                        moveTo(w * .08f, h * .73f)
                        lineTo(w * .58f, h * .73f)
                        cubicTo(w * .88f, h * .73f, w * .83f, h * 1.02f, w * .60f, h * .94f)
                    }
                    drawPath(wind, ink, style = pen)
                }
                "Hissedilen" -> {
                    val tube = Path().apply {
                        moveTo(w * .35f, h * .64f)
                        lineTo(w * .35f, h * .18f)
                        cubicTo(w * .35f, 0f, w * .65f, 0f, w * .65f, h * .18f)
                        lineTo(w * .65f, h * .64f)
                    }
                    drawPath(tube, ink, style = pen)
                    drawCircle(ink, w * .22f, Offset(w * .5f, h * .75f), style = pen)
                    drawLine(ink, Offset(w * .5f, h * .3f), Offset(w * .5f, h * .76f), 2.dp.toPx(), StrokeCap.Round)
                }
                else -> if (night) {
                    val outer = Path().apply { addOval(Rect(0f, 0f, w, h)) }
                    val inner = Path().apply { addOval(Rect(w * .3f, -h * .15f, w * 1.12f, h * .72f)) }
                    drawPath(Path.combine(PathOperation.Difference, outer, inner), ink)
                } else {
                    drawCircle(ink, w * .25f, center)
                    repeat(8) { i ->
                        val angle = i * Math.PI / 4
                        val dx = kotlin.math.cos(angle).toFloat()
                        val dy = kotlin.math.sin(angle).toFloat()
                        drawLine(ink, center + Offset(dx * w * .36f, dy * h * .36f),
                            center + Offset(dx * w * .47f, dy * h * .47f), 1.5.dp.toPx(), StrokeCap.Round)
                    }
                }
            }
        }
    }
}
