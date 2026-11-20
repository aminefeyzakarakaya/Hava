package com.example.hava.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.hava.domain.WeatherCondition
import kotlin.math.cos
import kotlin.math.sin
import com.example.hava.ui.theme.WeatherIconColors


/**
 * Tüm hava durumu ikonları için tek çizim. Boyutu modifier belirler, çizim boyuta göre ölçeklenir.
 * Açık renkli kartların üstünde bulut rengini [cloudColor] ile koyulaştırabilirsin.
 */
@Composable
fun WeatherIcon(
    condition: WeatherCondition,
    night: Boolean,
    modifier: Modifier = Modifier,
    cloudColor: Color = if (night) WeatherIconColors.CloudNight else WeatherIconColors.CloudDay
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        if (night) {
            drawMoon(w, h)
        } else if (condition.showsSun) {
            drawSun(w, h)
        }

        if (condition.showsCloud) drawCloud(w, h, cloudColor)

        when {
            condition.isRainy -> drawRain(w, h, condition)
            condition.isSnowy -> drawSnow(w, h)
            condition == WeatherCondition.FOG -> drawFog(w, h)
        }

        if (condition == WeatherCondition.THUNDERSTORM) drawBolt(w, h)
    }
}

private fun DrawScope.drawMoon(w: Float, h: Float) {
    val outside = Path().apply { addOval(Rect(w * .22f, h * .08f, w * .78f, h * .64f)) }
    val inside = Path().apply { addOval(Rect(w * .40f, h * .02f, w * .91f, h * .57f)) }
    drawPath(Path.combine(PathOperation.Difference, outside, inside), WeatherIconColors.Sun)
}

private fun DrawScope.drawSun(w: Float, h: Float) {
    val center = Offset(w * .52f, h * .35f)
    drawCircle(WeatherIconColors.Sun, w * .17f, center)
    repeat(8) { index ->
        val angle = index * Math.PI / 4.0
        val dx = cos(angle).toFloat()
        val dy = sin(angle).toFloat()
        drawLine(
            color = WeatherIconColors.Sun,
            start = Offset(center.x + dx * w * .21f, center.y + dy * w * .21f),
            end = Offset(center.x + dx * w * .27f, center.y + dy * w * .27f),
            strokeWidth = w * .028f,
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawCloud(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(w * .10f, h * .49f),
        size = Size(w * .80f, h * .28f),
        cornerRadius = CornerRadius(w * .14f)
    )
    drawCircle(color, w * .21f, Offset(w * .39f, h * .49f))
    drawCircle(color, w * .16f, Offset(w * .67f, h * .53f))
}

private fun DrawScope.drawRain(w: Float, h: Float, condition: WeatherCondition) {
    val color = if (condition == WeatherCondition.DRIZZLE) WeatherIconColors.Drizzle else WeatherIconColors.Rain
    repeat(4) { index ->
        val x = w * (.25f + index * .17f)
        drawLine(
            color = color,
            start = Offset(x, h * .82f),
            end = Offset(x - w * .04f, h * .94f),
            strokeWidth = w * .035f,
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawSnow(w: Float, h: Float) {
    val arm = w * .045f
    val stroke = w * .028f
    repeat(3) { index ->
        val c = Offset(w * (.31f + index * .19f), h * .87f)
        drawLine(WeatherIconColors.Snow, Offset(c.x - arm, c.y), Offset(c.x + arm, c.y), stroke, StrokeCap.Round)
        drawLine(WeatherIconColors.Snow, Offset(c.x, c.y - arm), Offset(c.x, c.y + arm), stroke, StrokeCap.Round)
        drawLine(
            WeatherIconColors.Snow,
            Offset(c.x - arm * .7f, c.y - arm * .7f),
            Offset(c.x + arm * .7f, c.y + arm * .7f),
            stroke * .75f,
            StrokeCap.Round
        )
    }
}

private fun DrawScope.drawFog(w: Float, h: Float) {
    repeat(2) { index ->
        val y = h * (.84f + index * .07f)
        drawLine(WeatherIconColors.Fog, Offset(w * .28f, y), Offset(w * .76f, y), w * .035f, StrokeCap.Round)
    }
}

private fun DrawScope.drawBolt(w: Float, h: Float) {
    val bolt = Path().apply {
        moveTo(w * .55f, h * .78f)
        lineTo(w * .44f, h * .91f)
        lineTo(w * .53f, h * .91f)
        lineTo(w * .48f, h * .99f)
        lineTo(w * .65f, h * .85f)
        lineTo(w * .56f, h * .85f)
        close()
    }
    drawPath(bolt, WeatherIconColors.Bolt)
}
