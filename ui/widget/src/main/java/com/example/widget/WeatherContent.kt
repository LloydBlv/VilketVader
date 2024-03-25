package com.example.widget

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.layout.Row
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.example.domain.Weather

@SuppressLint("ComposeModifierMissing")
@Composable
internal fun WeatherContent(weather: Weather, updateWeather: () -> Unit) {
    AppWidgetColumn(
        modifier = GlanceModifier
            .clickable(updateWeather),
    ) {
        Text(
            text = weather.location.name,
            style = TextStyle(
                fontSize = 18.sp,
                textAlign = TextAlign.End,
            ),
        )

        // The temperature
        Text(
            text = "${weather.temperature.current}°",
            style = TextStyle(
                fontSize = 48.sp,
            ),
            modifier = GlanceModifier.wrapContentSize(),
        )

        // The weather condition
        Text(
            text = weather.conditions.firstOrNull()?.name.orEmpty(),
            style = TextStyle(
                fontSize = 12.sp,
                textAlign = TextAlign.End,
            ),
        )

        // High and low temperatures with the feels-like temperature
        Row {
            Text(
                text = "${weather.temperature.max}° / ${weather.temperature.min}° Feels like ${weather.temperature.feelsLike}°",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }

        Text(
            text = "tors, 09:23",
            style = TextStyle(fontSize = 12.sp),
        )
    }
}
