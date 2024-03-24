package com.example.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.layout.Alignment
import androidx.glance.layout.size
import androidx.glance.text.Text
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.Weather
import com.example.domain.WeatherResult
import kotlinx.coroutines.launch


val LocalAppContextProvider = staticCompositionLocalOf<Context> {
    error("App context not provided")
}

class WeatherGlanceWidget : GlanceAppWidget() {

    companion object {
        private val thinMode = DpSize(120.dp, 120.dp)
        private val smallMode = DpSize(184.dp, 184.dp)
        private val mediumMode = DpSize(260.dp, 200.dp)
        private val largeMode = DpSize(260.dp, 280.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(thinMode, smallMode, mediumMode, largeMode),
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val appContext = LocalContext.current.applicationContext
        val size = LocalSize.current
        val scope = rememberCoroutineScope()
        val usecase = getObserveLocationUseCase(appContext)
        val state by usecase.flow.collectAsState(initial = WeatherResult.Loading)
        LaunchedEffect(key1 = Unit) {
            usecase.invoke(ObserveSelectedWeatherUseCase.Params())
        }
        val updateWeather: () -> Unit = { scope.launch { refreshWeather(appContext) } }

        GlanceTheme {
            CompositionLocalProvider(LocalAppContextProvider provides appContext) {

                WidgetContent(state, updateWeather)
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

            is WeatherResult.Success ->  {
                WeatherContent(weather = state.data,
                    updateWeather = updateWeather)
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
            modifier = GlanceModifier.size(32.dp)
        )
    }


}

