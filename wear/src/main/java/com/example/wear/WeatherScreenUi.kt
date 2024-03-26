package com.example.wear

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.scrollAway
import androidx.wear.compose.material.swipeable
import androidx.wear.tooling.preview.devices.WearDevices.LARGE_ROUND
import com.example.domain.models.Condition
import com.example.domain.models.Location
import com.example.domain.models.Temperature
import com.example.domain.models.Weather
import com.example.wear.presentation.theme.VilketVaderTheme
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent

@CircuitInject(WeatherScreen::class, SingletonComponent::class)
@Composable
fun WeatherScreenUi(
    state: WeatherScreen.UiState,
    modifier: Modifier = Modifier,
) {
    val listState = rememberScalingLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        timeText = { TimeText(modifier = Modifier.scrollAway(listState)) },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState,
            )
        },
        content = {
            WeatherContent(
                modifier = Modifier.fillMaxSize(),
                state = state,
                listState = listState,
            )
        },
    )
}

@Composable
private fun WeatherContent(
    state: WeatherScreen.UiState,
    listState: ScalingLazyListState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.weather != null -> {
                SwipeableViews(
                    listState = listState,
                    modifier = Modifier.fillMaxSize(),
                    weather = state.weather,
                )
            }

            state.failure != null -> {
                Text(
                    text = state.failure.localizedMessage ?: "Something went wrong",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            else -> {
                Text(
                    text = "Loading...",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableViews(
    weather: Weather,
    listState: ScalingLazyListState,
    modifier: Modifier = Modifier,
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val anchors = mapOf(0f to 0, 1f to -1) // Maps swipe progress to a state

    Box(
        modifier = modifier
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
            ),
    ) {
        when (swipeableState.currentValue) {
            0 -> WeatherInfo(weather = weather, listState = listState)
            else -> WeatherInfo1(weather)
        }
    }
}

@Preview(
    device = LARGE_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true,
)
@Composable
internal fun LoadingPreview() {
    VilketVaderTheme {
        WeatherScreenUi(
            state = WeatherScreen.UiState(
                isLoading = true,
                weather = null,
                eventSink = {},
            ),
        )
    }
}

@Preview(
    device = LARGE_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true,
)
@Composable
internal fun FailurePreview() {
    VilketVaderTheme {
        WeatherScreenUi(
            state = WeatherScreen.UiState(
                isLoading = false,
                weather = null,
                failure = Throwable("Something went wrong"),
                eventSink = {},
            ),
        )
    }
}

@Preview(
    device = LARGE_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true,
)
@Composable
internal fun SuccessPreview() {
    VilketVaderTheme {
        WeatherScreenUi(
            modifier = Modifier.fillMaxSize(),
            state = WeatherScreen.UiState(
                isLoading = false,
                weather = Weather.EMPTY.copy(
                    location = Location.EMPTY.copy(name = "Stockholm"),
                    temperature = Temperature.EMPTY.copy(current = 16f, feelsLike = 14f, min = 10f, max = 20f),
                    conditions = listOf(Condition("Sunny", "very sunny", Condition.Type.CLEAR)),

                ),
                eventSink = {},
            ),
        )
    }
}
