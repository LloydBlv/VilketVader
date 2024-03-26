package com.example.wear

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TitleCard
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import com.example.domain.Location
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent

@Composable
@CircuitInject(LocationsScreen::class, SingletonComponent::class)
fun LocationsListScreenUi(
    state: LocationsScreen.UiState,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink
    val listState = rememberScalingLazyListState()

    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        timeText = { TimeText(modifier = Modifier.scrollAway(listState)) },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState,
            )
        },
        content = {
            LocationsLazyList(
                lazyListState = listState,
                modifier = modifier,
                state = state,
                eventSink = eventSink,
            )
        },
    )
}

@Composable
private fun LocationsLazyList(
    state: LocationsScreen.UiState,
    eventSink: (LocationsScreen.Events) -> Unit,
    lazyListState: ScalingLazyListState,
    modifier: Modifier = Modifier,
) {
    val contentModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    ScalingLazyColumn(
        state = lazyListState,
        autoCentering = AutoCenteringParams(itemIndex = 0),
        modifier = modifier.fillMaxSize(),
    ) {
        items(state.locations) { location ->
            LocationCard(
                modifier = contentModifier,
                location = location,
                onClick = { eventSink(LocationsScreen.Events.OnLocationClicked(location)) },
            )
        }
    }
}

@Composable
fun LocationCard(
    location: Location,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TitleCard(
        modifier = modifier,
        onClick = onClick,
        title = { Text(text = location.name) },
        content = {
            Text(text = location.country)
        },
    )
}
