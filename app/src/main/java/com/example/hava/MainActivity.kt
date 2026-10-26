package com.example.hava

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hava.ui.CityViewModel
import com.example.hava.ui.HavaSplashScreen
import com.example.hava.ui.SehirListesi
import com.example.hava.ui.WeatherBackground
import com.example.hava.ui.theme.HavaNavy
import com.example.hava.ui.theme.HavaSun
import com.example.hava.ui.theme.HavaTextLight
import com.example.hava.ui.theme.HavaTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val cityViewModel: CityViewModel = viewModel()
            val hava = cityViewModel.havaDurumu
            var splashGoster by rememberSaveable { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1400)
                splashGoster = false
            }

            HavaTheme(darkTheme = hava?.isDay == 0) {
                if (splashGoster) {
                    HavaSplashScreen()
                } else {
                    val metinRengi = if (hava?.isDay == 0) HavaTextLight else HavaNavy

                    WeatherBackground(hava = hava) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            containerColor = Color.Transparent,
                            contentColor = metinRengi,
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(38.dp)
                                                    .background(Color(0xFF9BD1FF), CircleShape),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "☼",
                                                    color = HavaNavy,
                                                    fontSize = 25.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                                                Text(
                                                    text = "Hava",
                                                    style = MaterialTheme.typography.titleLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = metinRengi
                                                )
                                                Text(
                                                    text = "Hava durumun hep yanında",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Medium,
                                                    color = metinRengi.copy(alpha = 0.72f)
                                                )
                                            }
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = Color.Transparent,
                                        titleContentColor = metinRengi
                                    )
                                )
                            }
                        ) { innerPadding ->
                            SehirListesi(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}
