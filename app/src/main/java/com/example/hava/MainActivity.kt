package com.example.hava

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hava.ui.components.WeatherBackground
import com.example.hava.ui.home.HomeScreen
import com.example.hava.ui.home.HomeViewModel
import com.example.hava.ui.home.WeatherUiState
import com.example.hava.ui.theme.HavaNavy
import com.example.hava.ui.theme.HavaTextLight
import com.example.hava.ui.theme.HavaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            val weatherState by homeViewModel.weatherState.collectAsStateWithLifecycle()
            val currentWeather = (weatherState as? WeatherUiState.Success)?.weather?.current
            val isNight = currentWeather?.isDay == 0

            HavaTheme(darkTheme = isNight) {
                WeatherBackground(weather = currentWeather) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        contentColor = if (isNight) HavaTextLight else HavaNavy,
                        topBar = { HavaTopBar(isNight = isNight) }
                    ) { innerPadding ->
                        HomeScreen(
                            viewModel = homeViewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HavaTopBar(isNight: Boolean) {
    val textColor = if (isNight) HavaTextLight else HavaNavy

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
                Column {
                    Text(
                        text = "Hava",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Text(
                        text = "Hava durumun hep yanında",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = textColor.copy(alpha = 0.72f)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = textColor
        )
    )
}
