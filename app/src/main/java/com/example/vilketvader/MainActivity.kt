package com.example.vilketvader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import com.example.domain.ObserveSelectedLocationUseCase
import com.example.screens.HomeScreen
import com.example.vilketvader.ui.theme.VilketVaderTheme
import com.example.weather.LocalDateFormatter
import com.example.widget.WeatherGlanceWidget
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var circuit: Circuit

    @Inject
    lateinit var observeSelectedLocationUseCase: ObserveSelectedLocationUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen: SplashScreen = installSplashScreen()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            VilketVaderTheme {
                CompositionLocalProvider(LocalDateFormatter provides DateFormatterDefault()) {
                    CircuitCompositionLocals(circuit = circuit) {
                        CircuitContent(screen = HomeScreen)
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            observeSelectedLocationUseCase.invoke(ObserveSelectedLocationUseCase.Params(forceFresh = false))
            observeSelectedLocationUseCase.flow.collect { result ->
                result.onSuccess { location ->
                    WeatherGlanceWidget().updateAll(this@MainActivity.applicationContext)
                }
                result.onFailure { exception ->
                    // Handle the exception
                }
            }
        }

    }

}