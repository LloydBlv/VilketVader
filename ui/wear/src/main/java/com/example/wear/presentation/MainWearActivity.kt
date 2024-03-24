/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wear.LocationsScreen
import com.example.wear.presentation.theme.VilketVaderTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainWearActivity : ComponentActivity() {

    @Inject
    lateinit var circuit: dagger.Lazy<Circuit>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            VilketVaderTheme {
                val backstack = rememberSaveableBackStack(root = LocationsScreen)
                val navigator = rememberCircuitNavigator(backstack)
                CircuitCompositionLocals(circuit = circuit.get()) {
                    NavigableCircuitContent(
                        modifier = Modifier.fillMaxSize(),
                        navigator = navigator,
                        backStack = backstack,
                    )
                }

            }
        }
    }
}
