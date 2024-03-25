package com.example.wear

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.domain.Icon
import com.example.domain.Weather

@Composable
internal fun WeatherInfo(
    weather: Weather,
    listState: ScalingLazyListState,
    modifier: Modifier = Modifier,
) {
    val contentModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    ScalingLazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        autoCentering = AutoCenteringParams(itemIndex = 0),
    ) {
        item { LocationName(weather, contentModifier) }
        item { WeatherIcon(icon = weather.icon, contentModifier) }
        item { Spacer(modifier = contentModifier) }

        item { CurrentTemperature(weather, contentModifier) }

        item { Condition(weather, contentModifier) }

        item { Spacer(modifier = contentModifier) }

        item { MinMaxTemperatures(weather, contentModifier) }

        item { FeelsLikeTemp(weather, contentModifier) }

        item { TimestampText(modifier = contentModifier) }
    }
}

@Composable
internal fun WeatherInfo1(weather: Weather, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
            modifier = Modifier.wrapContentWidth(),
        )

        // RealFeel
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WeatherIcon(icon = weather.icon)
            Text(
                text = "RealFeel ${weather.temperature.feelsLike}°",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 2.dp),
            )
        }
    }
}

@Composable
fun WeatherIcon(icon: Icon, modifier: Modifier = Modifier) {
    AsyncImage(
        model = icon,
        contentDescription = "weather icon",
        modifier = modifier.wrapContentWidth(),
    )
}

@Composable
private fun TimestampText(modifier: Modifier = Modifier) {
    Text(
        text = "tors, 09:23",
        fontSize = 12.sp,
        fontWeight = FontWeight.Thin,
        modifier = modifier.wrapContentWidth(),
    )
}

@Composable
private fun FeelsLikeTemp(weather: Weather, modifier: Modifier = Modifier) {
    Text(
        text = "Känns som ${weather.temperature.feelsLike}°",
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        modifier = modifier.wrapContentWidth(),
    )
}

@Composable
private fun MinMaxTemperatures(weather: Weather, modifier: Modifier = Modifier) {
    Text(
        text = "${weather.temperature.max}° / ${weather.temperature.min}°",
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        modifier = modifier.wrapContentWidth(),
    )
}

@Composable
private fun Condition(weather: Weather, modifier: Modifier = Modifier) {
    Text(
        text = weather.conditions.firstOrNull()?.name.orEmpty(),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        modifier = modifier.wrapContentWidth(),
    )
}

@Composable
private fun CurrentTemperature(weather: Weather, modifier: Modifier = Modifier) {
    Text(
        text = "${weather.temperature.current}°",
        fontSize = 32.sp,
        fontWeight = FontWeight.Light,
        modifier = modifier.wrapContentWidth(),
    )
}

@Composable
private fun LocationName(weather: Weather, modifier: Modifier = Modifier) {
    Text(
        text = weather.location.name,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = modifier.wrapContentWidth(),
    )
}
