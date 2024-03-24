package com.example.wear

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.domain.Icon
import com.example.domain.Weather


@Composable
internal fun WeatherInfo(weather: Weather, modifier: Modifier = Modifier) {
    ScalingLazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        autoCentering = AutoCenteringParams(0, 0)
    ) {
        item { LocationName(weather) }
        item { WeatherIcon(icon = weather.icon) }
        item { Spacer(modifier = Modifier.height(8.dp)) }

        item { CurrentTemperature(weather) }

        item { Condition(weather) }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item { MinMaxTemperatures(weather) }

        item { FeelsLikeTemp(weather) }

        item { TimestampText() }
    }
}
@Composable
internal fun WeatherInfo1(weather: Weather, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Time
//        TimeText(
//            timeTextStyle = TimeTextDefaults.timeTextStyle(
//                color = Color.White,
//                fontSize = 14.sp,
//            )
//        )
        Text(
            text = "${weather.temperature.current}°",
            fontSize = 44.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.wrapContentWidth()
        )

        // RealFeel
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherIcon(icon = weather.icon)
            Text(
                text = "RealFeel ${weather.temperature.feelsLike}°",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}

@Composable
fun WeatherIcon(icon: Icon, modifier: Modifier = Modifier) {
    AsyncImage(
        model = icon,
        contentDescription = "weather icon",
        modifier = modifier.wrapContentWidth()
    )
}

@Composable
private fun TimestampText() {
    Text(
        text = "tors, 09:23",
        fontSize = 12.sp,
        fontWeight = FontWeight.Thin,
        modifier = Modifier.wrapContentWidth()
    )
}

@Composable
private fun FeelsLikeTemp(weather: Weather) {
    Text(
        text = "Känns som ${weather.temperature.feelsLike}°",
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.wrapContentWidth()
    )
}

@Composable
private fun MinMaxTemperatures(weather: Weather) {
    Text(
        text = "${weather.temperature.max}° / ${weather.temperature.min}°",
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.wrapContentWidth()
    )
}

@Composable
private fun Condition(weather: Weather) {
    Text(
        text = weather.conditions.firstOrNull()?.name.orEmpty(),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.wrapContentWidth()
    )
}

@Composable
private fun CurrentTemperature(weather: Weather) {
    Text(
        text = "${weather.temperature.current}°",
        fontSize = 30.sp,
        fontWeight = FontWeight.Light,
        modifier = Modifier.wrapContentWidth()
    )
}

@Composable
private fun LocationName(weather: Weather) {
    Text(
        text = weather.location.name,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.wrapContentWidth()
    )
}