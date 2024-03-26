package com.example.widget.composables

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import com.example.domain.models.Weather
import com.example.domain.models.WeatherResult
import com.example.domain.usecases.ObserveSelectedWeatherUseCase
import com.example.widget.AppWidgetBox
import com.example.widget.AppWidgetColumn
import com.example.widget.LocalAppContextProvider
import com.example.widget.WeatherContent
import com.example.widget.WeatherGlanceWidget
import com.example.widget.getObserveLocationUseCase
import com.example.widget.getRefreshWeather
import kotlinx.coroutines.launch

@Composable
internal fun Content() {
    val appContext = LocalContext.current.applicationContext
    val size = LocalSize.current
    val scope = rememberCoroutineScope()
    val usecase = getObserveLocationUseCase(appContext)
    val state by usecase.flow.collectAsState(initial = WeatherResult.Loading)
    LaunchedEffect(key1 = Unit) {
        usecase.invoke(ObserveSelectedWeatherUseCase.Params())
    }
    val updateWeather: () -> Unit = { scope.launch { refreshWeather(appContext) } }

    MaterialTheme(darkColorScheme()) {
        CompositionLocalProvider(LocalAppContextProvider provides appContext) {
            Box(
                modifier = GlanceModifier.background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                WidgetContent(state = state, updateWeather = updateWeather)
            }
        }
    }
}

private suspend fun refreshWeather(context: Context) {
    WeatherGlanceWidget().updateAll(context)
    getRefreshWeather(context).invoke(Unit)
}

@SuppressLint("ComposeModifierMissing")
@Composable
internal fun WidgetContent(state: WeatherResult<Weather>, updateWeather: () -> Unit) {
    when (state) {
        is WeatherResult.Failure -> {
            AppWidgetColumn(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                FailureUi(state.throwable?.message)

                Button("Refresh", updateWeather)
            }
        }

        WeatherResult.Loading -> {
            AppWidgetBox(contentAlignment = Alignment.Center) {
                LoadingUi()
            }
        }

        is WeatherResult.Success -> {
            WeatherContent(
                weather = state.data,
                updateWeather = updateWeather,
            )
        }
    }
}

@Composable
private fun FailureUi(message: String?) {
    Text(text = message ?: "Something went wrong!")
}

@Composable
private fun LoadingUi() {
    CircularProgressIndicator(
        modifier = GlanceModifier.size(32.dp),
    )
}
