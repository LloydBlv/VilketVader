package com.example.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import com.example.domain.ObserveSelectedWeatherUseCase
import com.example.domain.Weather
import com.example.domain.WeatherResult


val LocalAppContextProvider = staticCompositionLocalOf<Context> {
    error("App context not provided")
}
class WeatherGlanceWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                val appContext = context.applicationContext
                CompositionLocalProvider(LocalAppContextProvider provides appContext) {
                    val usecase = getObserveLocationUseCase(appContext)
                    LaunchedEffect(key1 = Unit) {
                        usecase.invoke(ObserveSelectedWeatherUseCase.Params())
                    }
                    val state by usecase.flow.collectAsState(initial = WeatherResult.Loading)
                    WidgetContent(state)
                }

            }
        }
    }

    @SuppressLint("ComposeModifierMissing")
    @Composable
    internal fun WidgetContent(state: WeatherResult<Weather>) {
        Box(GlanceModifier.fillMaxSize().appWidgetBackground().cornerRadius(16.dp)) {
            when (state) {
                is WeatherResult.Failure -> FailureUi(state.throwable?.message)
                WeatherResult.Loading -> LoadingUi()
                is WeatherResult.Success -> WeatherContent(weather = state.data)
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

