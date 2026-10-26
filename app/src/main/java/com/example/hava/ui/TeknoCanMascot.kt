package com.example.hava.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hava.ui.theme.HavaNavy
import com.example.hava.ui.theme.HavaSun

@Composable
fun TeknoCanCocuk(modifier: Modifier = Modifier) {
    val lacivert = HavaNavy
    val pantolon = Color(0xFFE9D7B4)

    Box(modifier = modifier) {
        Row(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 3.dp, height = 20.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(HavaSun)
            )
            Box(
                modifier = Modifier
                    .size(width = 3.dp, height = 20.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(HavaSun)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-3).dp)
                .size(width = 46.dp, height = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier.size(8.dp).clip(CircleShape).background(HavaSun))
            Box(Modifier.size(8.dp).clip(CircleShape).background(HavaSun))
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 14.dp)
                .size(width = 78.dp, height = 64.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(HavaSun)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 36.dp, height = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(lacivert))
                Box(Modifier.size(6.dp).clip(CircleShape).background(lacivert))
            }
            Text(
                text = "⌣",
                modifier = Modifier.align(Alignment.BottomCenter),
                color = lacivert,
                fontSize = 20.sp,
                lineHeight = 20.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 76.dp)
                .size(width = 52.dp, height = 34.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(lacivert)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-30).dp, y = 79.dp)
                .rotate(-24f)
                .size(width = 8.dp, height = 22.dp)
                .clip(CircleShape)
                .background(lacivert)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 30.dp, y = 75.dp)
                .rotate(28f)
                .size(width = 8.dp, height = 22.dp)
                .clip(CircleShape)
                .background(lacivert)
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 107.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 18.dp, height = 15.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(pantolon)
            )
            Box(
                modifier = Modifier
                    .size(width = 18.dp, height = 15.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(pantolon)
            )
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 25.dp, height = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFFFEFA))
            )
            Box(
                modifier = Modifier
                    .size(width = 25.dp, height = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFFFEFA))
            )
        }
    }
}
