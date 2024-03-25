package com.example.wear

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
import com.example.domain.Weather
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
                    text = "Error",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
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
