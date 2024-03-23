package com.example.widget

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.domain.Weather

@SuppressLint("ComposeModifierMissing")
@Composable
internal fun WeatherContent(weather: Weather) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f))
            .cornerRadius(16.dp)
            .padding(16.dp)
    ) {
        Text(
            text = weather.location.name,
            style = TextStyle(fontSize = 18.sp)
        )

        // The temperature
        Text(
            text = "${weather.temperature.current}°" ,
            style = TextStyle(fontSize = 32.sp)
        )

        // The weather condition
        Text(
            text = weather.conditions.firstOrNull()?.name.orEmpty(),
            style = TextStyle(fontSize = 18.sp)
        )

        // High and low temperatures with the feels-like temperature
        Row {
            Text(
                text = "${weather.temperature.max}° / ${weather.temperature.min}° Feels like ${weather.temperature.feelsLike}°",
                style = TextStyle(fontSize = 14.sp)
            )
        }

        Text(
            text = "tors, 09:23",
            style = TextStyle(fontSize = 12.sp)
        )

        Spacer(modifier = GlanceModifier.height(10.dp))
        
        Button(
            text = "Refresh",
            onClick = actionRunCallback<RefreshWeatherAction>()
        )
    }
}