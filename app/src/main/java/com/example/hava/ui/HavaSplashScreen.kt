package com.example.hava.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text

@Composable
fun HavaSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF79BDF0), Color(0xFFB9DDF5), Color(0xFFE6EAFE))
                )
            )
    ) {
        Canvas(Modifier.fillMaxSize()) {
            // Light, rounded clouds keep the welcome screen airy without crowding the mascot.
            drawCircle(Color.White.copy(alpha = .32f), size.width * .21f,
                Offset(size.width * .03f, size.height * .18f))
            drawCircle(Color.White.copy(alpha = .24f), size.width * .15f,
                Offset(size.width * .98f, size.height * .31f))
            drawCircle(Color(0xFFFFD56F).copy(alpha = .75f), size.width * .085f,
                Offset(size.width * .83f, size.height * .18f))
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(width = 230.dp, height = 205.dp),
                contentAlignment = Alignment.Center
            ) {
                TeknoCanCocuk(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(width = 146.dp, height = 128.dp)
                )
                Canvas(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(94.dp)
                ) {
                    val cloud = Color(0xFFF8FCFF)
                    drawRoundRect(
                        color = cloud,
                        topLeft = Offset(size.width * .08f, size.height * .47f),
                        size = Size(size.width * .84f, size.height * .58f),
                        cornerRadius = CornerRadius(size.height * .28f)
                    )
                    drawCircle(cloud, size.height * .34f,
                        Offset(size.width * .33f, size.height * .52f))
                    drawCircle(cloud, size.height * .40f,
                        Offset(size.width * .59f, size.height * .45f))
                    drawCircle(cloud, size.height * .30f,
                        Offset(size.width * .78f, size.height * .55f))
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(
                text = "Hava",
                color = Color(0xFF15385D),
                fontSize = 52.sp,
                lineHeight = 58.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1.5).sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Güne hazır ol",
                color = Color(0xFF315F87),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
