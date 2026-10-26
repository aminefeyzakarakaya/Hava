package com.example.hava.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.R

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
            drawCircle(
                color = Color.White.copy(alpha = .30f),
                radius = size.width * .20f,
                center = Offset(size.width * .02f, size.height * .16f)
            )
            drawCircle(
                color = Color.White.copy(alpha = .22f),
                radius = size.width * .15f,
                center = Offset(size.width * .98f, size.height * .30f)
            )
            drawCircle(
                color = Color(0xFFFFD56F).copy(alpha = .76f),
                radius = size.width * .08f,
                center = Offset(size.width * .84f, size.height * .18f)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.teknocan_cloud),
                contentDescription = "Buluttan bakan TeknoCan",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp)
            )
            Spacer(Modifier.height(6.dp))
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
