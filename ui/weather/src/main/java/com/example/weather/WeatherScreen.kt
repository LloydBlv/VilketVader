package com.example.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.screens.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@CircuitInject(WeatherScreen::class, SingletonComponent::class)
fun WeatherScreenUi(
    state: WeatherUiState,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink
    Scaffold(
        containerColor = Color.Transparent,
        modifier = modifier,
        content = {
            BoxWithSwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                onSwipe = { eventSink(WeatherEvent.Refresh) },
                isRefreshing = (state as? WeatherUiState.Success)?.isRefreshing == true,
            ) {
                WeatherScreenContent(state)
            }
        },
    )
}

@Composable
private fun BoxScope.WeatherScreenContent(state: WeatherUiState, modifier: Modifier = Modifier) {
    Timber.i("WeatherScreenContent: state=$state")
    when (state) {
        is WeatherUiState.Success -> {
            WeatherListUi(state, modifier = Modifier.fillMaxSize())
        }

        is WeatherUiState.Failure -> {
            Text(
                text = state.error?.localizedMessage ?: "Failed to load weather data",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
            )
        }

        WeatherUiState.Loading -> {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("loading"),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

@Composable
private fun WeatherListUi(state: WeatherUiState.Success, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .testTag("weather_list")
            .padding(8.dp),
    ) {
        item { CurrentTemperatureCard(state, Modifier.padding(8.dp)) }
        item { Spacer(modifier = Modifier.height(40.dp)) }
        item { MinMaxTemperature(state, Modifier.padding(8.dp)) }
        item { TimeStampText(state, Modifier.padding(8.dp)) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { WindAndHumidityCard(state) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { PressureAndVisibilityCards(state) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { CloudCard(state) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
    }
    if (state.weather.isRainy()) {
        RainAnimationScreen()
    }
}

@Composable
private fun PressureAndVisibilityCards(state: WeatherUiState.Success) {
    Row(modifier = Modifier.fillMaxWidth()) {
        WeatherInfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Compress,
            fieldName = "Pressure",
            fieldValue = "${state.weather?.pressure} hg",
        )
        Spacer(
            modifier = Modifier
                .height(5.dp)
                .weight(0.05f),
        )
        WeatherInfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.ViewCozy,
            fieldName = "Visibility",
            fieldValue = "${state.weather?.visibility}m",
        )
    }
}

@Composable
private fun CloudCard(state: WeatherUiState.Success) {
    Row(modifier = Modifier.fillMaxWidth()) {
        WeatherInfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.WbCloudy,
            fieldName = "Clouds",
            fieldValue = "${state.weather?.clouds}%",
        )
        Spacer(
            modifier = Modifier
                .height(5.dp)
                .weight(0.05f),
        )
        Box(modifier = Modifier.weight(1f)) {
        }
    }
}

@Composable
private fun WindAndHumidityCard(state: WeatherUiState.Success) {
    Row(modifier = Modifier.fillMaxWidth()) {
        WeatherInfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.WindPower,
            fieldName = "Wind",
            fieldValue = "${state.weather?.wind?.speed} m/s",
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .weight(0.05f),
        )
        WeatherInfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.WaterDrop,
            fieldName = "Humidity",
            fieldValue = "${state.weather?.humidity}%",
        )
    }
}

@Composable
private fun TimeStampText(state: WeatherUiState.Success, modifier: Modifier = Modifier) {
    val formatter = LocalDateFormatter.current
    Text(
        modifier = modifier,
        text = state.weather?.timestamp?.let(formatter::formatWeekDayAndTime).orEmpty(),
        color = Color.White.copy(alpha = 0.6f),
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
private fun MinMaxTemperature(
    state: WeatherUiState.Success,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = stringResource(
            R.string.min_max_feelslike_placeholder,
            state.weather.temperature.max,
            state.weather.temperature.min,
            state.weather.temperature.feelsLike,
        ),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun CurrentTemperatureCard(
    state: WeatherUiState.Success,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Column {
            Text(
                text = stringResource(
                    R.string.degrees_with_sign,
                    state.weather.temperature.current,
                ),
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 67.sp),
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${state.weather.conditions.firstOrNull()?.name}",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "${state.weather.conditions.firstOrNull()?.description}",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White.copy(alpha = 0.6f),
            )
        }
        AsyncImage(
            modifier = Modifier.size(120.dp),
            model = state.weather.icon,
            contentDescription = null,
        )
    }
}

@Composable
fun WeatherInfoCard(
    icon: ImageVector,
    fieldName: String,
    fieldValue: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.15f),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = fieldName,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White.copy(alpha = 0.6f),
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = fieldValue,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
private fun WeatherScreenUiPreview() {
//    VilketVaderTheme {
//        WeatherScreenUi(location = UiLocation("Stockholm"))
//    }
}
