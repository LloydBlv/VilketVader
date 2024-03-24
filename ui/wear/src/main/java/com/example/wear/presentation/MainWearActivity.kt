/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.wear.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.Weather
import com.example.domain.WeatherResult
import com.example.wear.WeatherInfo
import com.example.wear.presentation.theme.VilketVaderTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainWearActivity : ComponentActivity() {

    @Inject
    lateinit var observeSelectedWeather: ObserveSelectedWeatherUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }

    @SuppressLint("ComposeModifierMissing")
    @Composable
    fun WearApp() {
        VilketVaderTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {
                TimeText()
                WeatherApp()
            }
        }
    }

    @Composable
    fun WeatherApp(modifier: Modifier = Modifier) {
        val state by observeSelectedWeather.flow.collectAsState(initial = WeatherResult.Loading)
        LaunchedEffect(key1 = Unit) {
            observeSelectedWeather.invoke(ObserveSelectedWeatherUseCase.Params())
        }

        Box(modifier = modifier.fillMaxSize()) {
            when(state) {
                is WeatherResult.Success -> {
                    WeatherInfo(weather = (state as WeatherResult.Success<Weather>).data)
                }
                is WeatherResult.Failure -> {
                    Text(
                        text = "Error",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                else -> {
                    Text(
                        text = "Loading...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}

